package com.example.profile.data;

import com.example.profile.data.dto.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, UUID> {
}
