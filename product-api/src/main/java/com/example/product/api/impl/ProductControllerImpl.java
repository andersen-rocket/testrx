package com.example.product.api.impl;

import com.example.product.api.ProductController;
import com.example.product.data.dto.Product;
import com.example.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public Flux<Product> list() {
        return productService.list();
    }

    @Override
    public Mono<Product> get(String id) {
        return productService.get(UUID.fromString(id));
    }

    @Override
    public Mono<Product> create(Product product) {
        return productService.create(product);
    }

    @Override
    public Mono<Product> update(Product product) {
        return productService.update(product);
    }

    @Override
    public Mono delete(String id) {
        return productService.delete(UUID.fromString(id));
    }
}
