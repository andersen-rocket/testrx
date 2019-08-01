package com.example.profile.api;

import com.example.profile.data.dto.Profile;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/profile")
public interface ProfileController {
    @GetMapping
    Flux<Profile> list();

    @GetMapping("/{id}")
    Mono<Profile> get(@PathVariable("id")  String id);

    @PostMapping
    Mono<Profile> create(@RequestBody Profile product);

    @PutMapping
    Mono<Profile> update(@RequestBody Profile product);

    @DeleteMapping("/{id}")
    Mono delete(@PathVariable("id")  String id);
}
