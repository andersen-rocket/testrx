package com.example.recommendation.api;

import com.example.recommendation.data.dto.ProductsRecommendation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequestMapping("/recommendation")
public interface RecommendationController {

	@GetMapping("/{profileId}/{sum}")
	Mono<ProductsRecommendation> get(@PathVariable("profileId") String profileId, @PathVariable("sum") BigDecimal sum);

}
