package com.example.recommendation.service;

import com.example.recommendation.data.dto.ProductsRecommendation;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface RecommendationService {

	Mono<ProductsRecommendation> get(String profileId, BigDecimal sum);

}
