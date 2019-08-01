package com.example.recommendation.client.impl;

import com.example.recommendation.client.Client;
import com.example.recommendation.client.config.HostConfig;
import com.example.recommendation.data.dto.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ProfileClient implements Client<Profile, String> {

	private static final String URI = "/profile";
	private static final String ID = "/{id}";

	private final HostConfig hostConfig;

	private WebClient client;

	@PostConstruct
	void init() {
		client = WebClient.create(hostConfig.getHostProfile());
	}

	@Override
	public Mono<Profile> get(String id) {
		return client.get()
				.uri(URI + ID, id)
				.retrieve()
				.bodyToMono(Profile.class);
	}

	@Override
	public Flux<Profile> getAll() {
		return client.get()
				.uri(URI)
				.retrieve()
				.bodyToFlux(Profile.class);
	}
}
