package com.example.recommendation.api.impl;

import com.example.recommendation.api.RecommendationController;
import com.example.recommendation.data.dto.ProductsRecommendation;
import com.example.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class RecommendationControllerImpl implements RecommendationController {

	private final RecommendationService recommendationService;

	@Override
	public Mono<ProductsRecommendation> get(String profileId, BigDecimal sum) {
		return recommendationService.get(profileId, sum);
	}

}
