package com.dbln9.product;

import com.dbln9.clients.ProductCreateRequest;
import com.dbln9.clients.ProductGetByIdsRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<Product> createProduct(ProductCreateRequest productCreateRequest) {
        Product product = Product.builder()
                .title(productCreateRequest.title())
                .description(productCreateRequest.description())
                .price(productCreateRequest.price())
                .category(productCreateRequest.category())
                .imageUrl(productCreateRequest.imageUrl())
                .brand(productCreateRequest.brand())
                .rating(productCreateRequest.rating())
                .build();

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    public ResponseEntity<List<Product>> getAllProducts(PageRequest pageRequest) {
        Page<Product> products = productRepository.findAll(pageRequest);
        return ResponseEntity.ok(products.getContent());
    }

    public ResponseEntity<Product> getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<List<Product>> findByIds(ProductGetByIdsRequest productGetByIdsRequest) {
        List<Product> products = productRepository.findProductByIdIn(productGetByIdsRequest.ids());
        return ResponseEntity.ok(products);
    }
}
