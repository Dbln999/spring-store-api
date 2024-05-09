package com.dbln9.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product", url = "http://localhost:8081", path = "/api/v1")
public interface ProductClient {
    @PostMapping("/product")
    ResponseEntity<ProductAbstract> createProduct(@RequestBody ProductCreateRequest productCreateRequest);

    @PostMapping("/product/find-by-ids")
    ResponseEntity<List<ProductAbstract>> findProductsByIds(@RequestBody ProductGetByIdsRequest productGetByIdsRequest);
}
