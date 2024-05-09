package com.dbln9.customer.customer.product;

import com.dbln9.clients.ProductAbstract;
import com.dbln9.clients.ProductCreateRequest;
import com.dbln9.clients.ProductGetByIdsRequest;
import com.dbln9.customer.customer.Customer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<List<ProductAbstract>> getProductsFromTheCart (@RequestBody ProductGetByIdsRequest productIds) {
        return productService.getProductsFromTheCart(productIds);
    }

    @GetMapping("/add-product/{id}")
    public ResponseEntity<Customer> addProductToTheCart (@PathVariable("id") Long id, HttpServletRequest request) {
        return productService.addProductToTheCart(id, request);
    }

    @PostMapping("/admin/create-product")
    public ResponseEntity<ProductAbstract> addProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        return productService.createProduct(productCreateRequest);
    }
}
