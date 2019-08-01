package com.example.profile.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Profile {
    @Id
    UUID id;
    Integer risk;
}
