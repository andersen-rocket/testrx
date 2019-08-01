package com.example.recommendation.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Product {

	UUID id;

	BigDecimal price;

	Integer riskCategory;

	Type type;

}
