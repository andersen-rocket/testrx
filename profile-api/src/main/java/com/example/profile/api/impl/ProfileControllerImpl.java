package com.example.profile.api.impl;


import com.example.profile.api.ProfileController;
import com.example.profile.data.dto.Profile;
import com.example.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService productService;

    @Override
    public Flux<Profile> list() {
        return productService.list();
    }

    @Override
    public Mono<Profile> get(String id) {
        return productService.get(UUID.fromString(id));
    }

    @Override
    public Mono<Profile> create(Profile profile) {
        return productService.create(profile);
    }

    @Override
    public Mono<Profile> update(Profile profile) {
        return productService.update(profile);
    }

    @Override
    public Mono delete(String id) {
        return productService.delete(UUID.fromString(id));
    }
}
