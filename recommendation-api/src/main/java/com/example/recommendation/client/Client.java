package com.example.recommendation.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Client<T, ID> {

	Mono<T> get(ID id);

	Flux<T> getAll();

}
