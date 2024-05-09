package com.dbln9.customer.customer;

import com.dbln9.amqp.RabbitMQMessageProducer;
import com.dbln9.customer.customer.enums.Role;
import com.dbln9.customer.customer.responses.AuthRegisterResponse;
import com.dbln9.customer.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService { ;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public ResponseEntity<AuthRegisterResponse> registerCustomer(
            CustomerRegistrationRequest customerRegistrationRequest,
            String baseUrl
    ) {
        try {
            String randomCode = RandomString.make(64);
            Customer customer = Customer.builder()
                    .email(customerRegistrationRequest.email())
                    .password(passwordEncoder.encode(customerRegistrationRequest.password()))
                    .firstname(customerRegistrationRequest.firstname())
                    .lastname(customerRegistrationRequest.lastname())
                    .phone(customerRegistrationRequest.phone())
                    .city(customerRegistrationRequest.city())
                    .address(customerRegistrationRequest.address())
                    .postcode(customerRegistrationRequest.postcode())
                    .country(customerRegistrationRequest.country())
                    .role(Role.USER)
                    .productsInTheCard(new ArrayList<>())
                    .activated(false)
                    .verificationCode(randomCode)
                    .build();

            customerRepository.save(customer);

            CustomerMessage customerMessage = new CustomerMessage(
                    customer.getEmail(),
                    customer.getFirstname(),
                    customer.getLastname(),
                    customer.getVerificationCode(),
                    baseUrl
            );
            rabbitMQMessageProducer.publish(
                    customerMessage,
                    "internal.exchange",
                    "internal.email.routing-key"
            );
            return ResponseEntity.ok(
                    AuthRegisterResponse.builder()
                            .token("Waiting for verification")
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Customer service registration error", e);
        }
    }


    public ResponseEntity<AuthRegisterResponse> authenticateCustomer(
            CustomerAuthenticateRequest customerAuthenticateRequest
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            customerAuthenticateRequest.email(),
                            customerAuthenticateRequest.password()
                    )
            );
            Customer customer = customerRepository.findCustomerByEmail(customerAuthenticateRequest.email())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            String jwtToken = jwtService.generateToken(customer);
            return ResponseEntity.ok(
                    AuthRegisterResponse.builder()
                            .token(jwtToken)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Customer service authentication error", e);
        }
    }

    public ResponseEntity<Customer> giveCustomerAdmin(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setRole(Role.ADMIN);
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(savedCustomer);
    }



}
