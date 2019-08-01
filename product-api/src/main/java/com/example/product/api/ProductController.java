package com.example.product.api;

import com.example.product.data.dto.Product;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/product")
public interface ProductController {
    @GetMapping
    Flux<Product> list();

    @GetMapping("/{id}")
    Mono<Product> get(@PathVariable("id")  String id);

    @PostMapping
    Mono<Product> create(@RequestBody Product product);

    @PutMapping
    Mono<Product> update(@RequestBody Product product);

    @DeleteMapping("/{id}")
    Mono delete(@PathVariable("id")  String id);
}
