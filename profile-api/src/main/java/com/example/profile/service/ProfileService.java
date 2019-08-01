package com.example.profile.service;

import com.example.profile.data.dto.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProfileService {
    Mono<Profile> get(UUID id);
    Flux<Profile> list();
    Mono delete(UUID id);
    Mono<Profile> create(Profile product);
    Mono<Profile> update(Profile product);
}
