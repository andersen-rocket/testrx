package com.example.profile.service.impl;

import com.example.profile.data.ProfileRepository;
import com.example.profile.data.dto.Profile;
import com.example.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Mono<Profile> get(UUID id) {
        return profileRepository.findById(id);
    }

    @Override
    public Flux<Profile> list() {
        return profileRepository.findAll();
    }

    @Override
    public Mono delete(UUID id) {
        return profileRepository.deleteById(id);
    }

    @Override
    public Mono<Profile> create(Profile profile) {
        return profileRepository.save(profile.setId(UUID.randomUUID()));
    }

    @Override
    public Mono<Profile> update(Profile profile) {
        return profileRepository.existsById(profile.getId())
                .flatMap( exists ->
                exists ? profileRepository.save(profile) : Mono.error(new RuntimeException("Entity not found"))
        );
    }
}
