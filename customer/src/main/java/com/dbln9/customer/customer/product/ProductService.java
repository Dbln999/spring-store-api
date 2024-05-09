package com.dbln9.customer.customer.product;

import com.dbln9.clients.ProductAbstract;
import com.dbln9.clients.ProductClient;
import com.dbln9.clients.ProductCreateRequest;
import com.dbln9.clients.ProductGetByIdsRequest;
import com.dbln9.customer.customer.Customer;
import com.dbln9.customer.customer.CustomerRepository;
import com.dbln9.customer.customer.CustomerService;
import com.dbln9.customer.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private final ProductClient productClient;
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;


    public ResponseEntity<List<ProductAbstract>> getProductsFromTheCart(ProductGetByIdsRequest productIds) {
        return productClient.findProductsByIds(productIds);
    }

    public ResponseEntity<ProductAbstract> createProduct(ProductCreateRequest productCreateRequest) {
        return productClient.createProduct(productCreateRequest);
    }

    public ResponseEntity<Customer> addProductToTheCart(Long id, HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        jwt = authHeader.substring(7);
        String userId = jwtService.extractId(jwt);
        Customer customer = customerRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Long> cartProducts = customer.getProductsInTheCard();
        cartProducts.add(id);
        customer.setProductsInTheCard(cartProducts);
        Customer save = customerRepository.save(customer);
        return ResponseEntity.ok(save);
    }
}
