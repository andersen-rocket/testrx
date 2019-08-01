package com.example.product.service.impl;

import com.example.product.data.ProductRepository;
import com.example.product.data.dto.Product;
import com.example.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> get(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Flux<Product> list() {
        return productRepository.findAll();
    }

    @Override
    public Mono delete(UUID id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.save(product.setId(UUID.randomUUID()));
    }

    @Override
    public Mono<Product> update(Product product) {
        return productRepository.existsById(product.getId())
                .flatMap( exists ->
                    exists ? productRepository.save(product) : Mono.error(new RuntimeException("Entity not found"))
                 );
    }
}
