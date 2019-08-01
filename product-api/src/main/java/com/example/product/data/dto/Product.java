package com.example.product.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class Product {
    @Id
    UUID id;
    BigDecimal price;
    Integer riskCategory;
    Type type;
}
