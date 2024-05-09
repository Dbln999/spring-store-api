package com.dbln9.customer.customer;

import com.dbln9.customer.customer.responses.AuthRegisterResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponse> registerCustomer (
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest,
            HttpServletRequest httpServletRequest
    ) {
        String baseUrl = httpServletRequest.getRequestURL().toString();
        baseUrl = baseUrl.replaceAll(httpServletRequest.getServletPath(), "");

        return customerService.registerCustomer(customerRegistrationRequest, baseUrl);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthRegisterResponse> authenticateCustomer (
            @RequestBody CustomerAuthenticateRequest customerAuthenticateRequest
    ) {
        return customerService.authenticateCustomer(customerAuthenticateRequest);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Customer> giveCustomerAdmin (@PathVariable("id") Long id) {
        return customerService.giveCustomerAdmin(id);
    }


}
