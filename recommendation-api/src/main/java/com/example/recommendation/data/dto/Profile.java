package com.example.recommendation.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Profile {

	UUID id;

	Integer risk;

}
