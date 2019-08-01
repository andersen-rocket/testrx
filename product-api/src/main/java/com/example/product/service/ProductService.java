package com.example.product.service;

import com.example.product.data.dto.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductService {
    Mono<Product> get(UUID id);
    Flux<Product> list();
    Mono delete(UUID id);
    Mono<Product> create(Product product);
    Mono<Product> update(Product product);
}
