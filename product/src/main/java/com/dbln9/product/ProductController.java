package com.dbln9.product;

import com.dbln9.clients.ProductCreateRequest;
import com.dbln9.clients.ProductGetByIdsRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "10", name = "size") Integer size
    ) {
        return productService.getAllProducts(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest productCreateRequest) {
        return productService.createProduct(productCreateRequest);
    }

    @PostMapping("/find-by-ids")
    public ResponseEntity<List<Product>> findProductsByIds(@RequestBody ProductGetByIdsRequest productGetByIdsRequest) {
        return productService.findByIds(productGetByIdsRequest);
    }
}
