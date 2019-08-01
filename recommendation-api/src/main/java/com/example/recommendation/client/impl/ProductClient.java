package com.example.recommendation.client.impl;

import com.example.recommendation.client.Client;
import com.example.recommendation.client.config.HostConfig;
import com.example.recommendation.data.dto.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ProductClient implements Client<Product, String> {

	private static final String URI = "/product";
	private static final String ID = "/{id}";

	private final HostConfig hostConfig;

	private WebClient client;

	@PostConstruct
	void init() {
		client = WebClient.create(hostConfig.getHostProduct());
	}

	@Override
	public Mono<Product> get(String id) {
		return client.get()
				.uri(URI + ID, id)
				.retrieve()
				.bodyToMono(Product.class);
	}

	@Override
	public Flux<Product> getAll() {
		return client.get()
				.uri(URI)
				.retrieve()
				.bodyToFlux(Product.class);
	}
}
