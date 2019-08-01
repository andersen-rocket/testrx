package com.example.recommendation.service.impl;

import com.example.recommendation.client.impl.ProductClient;
import com.example.recommendation.client.impl.ProfileClient;
import com.example.recommendation.data.dto.Product;
import com.example.recommendation.data.dto.ProductsRecommendation;
import com.example.recommendation.data.dto.Profile;
import com.example.recommendation.data.dto.Type;
import com.example.recommendation.service.RecommendationService;
import com.example.recommendation.service.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
public class RecommendationServiceImplTest {

	private RecommendationService recommendationService;

	@Mock
	private ProfileClient profileClient;

	@Mock
	private ProductClient productClient;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		recommendationService = new RecommendationServiceImpl(profileClient, productClient);
	}

	@Test
	public void getTest_given() {
		given(profileClient.get(any(String.class))).willAnswer(invocation -> getProfileMono(4));
		given(productClient.getAll()).willAnswer(invocation -> getProductFlux());

		BigDecimal sum = new BigDecimal(200L);
		Mono<ProductsRecommendation> result = recommendationService.get(UUID.randomUUID().toString(), sum);

		Assert.assertNotNull(result);
		BigDecimal inc = result.map(prs -> prs.getProducts().stream().filter(p -> p.getType().equals(Type.INCOME)).map(Product::getPrice).reduce(BigDecimal.ZERO, ArithmeticUtils::add)).block();
		BigDecimal ins = result.map(prs -> prs.getProducts().stream().filter(p -> p.getType().equals(Type.INCOME)).map(Product::getPrice).reduce(BigDecimal.ZERO, ArithmeticUtils::add)).block();
		Assert.assertNotNull(inc);
		Assert.assertNotNull(ins);
		Assert.assertEquals(0, inc.compareTo(ins));
		BigDecimal sumResult = inc.add(ins);
		Assert.assertEquals(0, sumResult.compareTo(Objects.requireNonNull(result.map(ProductsRecommendation::getSum).block())));
		Assert.assertTrue(sumResult.compareTo(sum) <= 0);

		result.subscribe(c -> {
			log.info(c.getSum().toString());
			c.getProducts().forEach(p -> log.info(p.toString()));
		});
	}

	@Test(expected = DataNotFoundException.class)
	public void getTest_givenNotProfile() {
		given(profileClient.get(any(String.class))).willAnswer(invocation -> null);
		given(productClient.getAll()).willAnswer(invocation -> getProductFlux());

		BigDecimal sum = new BigDecimal(200L);
		Mono<ProductsRecommendation> result = recommendationService.get(UUID.randomUUID().toString(), sum);
	}

	@Test
	public void getTest_givenProvileWithOverRisk() {
		given(profileClient.get(any(String.class))).willAnswer(invocation -> getProfileMono(2));
		given(productClient.getAll()).willAnswer(invocation -> getProductFlux());

		BigDecimal sum = new BigDecimal(200L);
		Mono<ProductsRecommendation> result = recommendationService.get(UUID.randomUUID().toString(), sum);

		Assert.assertNotNull(result);
		BigDecimal inc = result.map(prs -> prs.getProducts().stream().filter(p -> p.getType().equals(Type.INCOME)).map(Product::getPrice).reduce(BigDecimal.ZERO, ArithmeticUtils::add)).block();
		BigDecimal ins = result.map(prs -> prs.getProducts().stream().filter(p -> p.getType().equals(Type.INCOME)).map(Product::getPrice).reduce(BigDecimal.ZERO, ArithmeticUtils::add)).block();
		Assert.assertNotNull(inc);
		Assert.assertNotNull(ins);
		Assert.assertEquals(0, inc.compareTo(ins));
		BigDecimal sumResult = inc.add(ins);
		Assert.assertEquals(0, sumResult.compareTo(Objects.requireNonNull(result.map(ProductsRecommendation::getSum).block())));
		Assert.assertTrue(sumResult.compareTo(sum) <= 0);

		result.subscribe(c -> {
			log.info(c.getSum().toString());
			c.getProducts().forEach(p -> log.info(p.toString()));
		});
	}

	private Mono<Profile> getProfileMono(int risk) {
		Profile profile = new Profile().setId(UUID.randomUUID()).setRisk(risk);
		return Mono.just(profile);
	}

	private Flux<Product> getProductFlux() {
		return Flux.just(
				getProduct(20, 10, Type.INCOME),
				getProduct(40, 3, Type.INCOME),
				getProduct(60, 4, Type.INCOME),
				getProduct(80, 5, Type.INCOME),
				getProduct(20, 2, Type.INSURANCE),
				getProduct(20, 3, Type.INSURANCE),
				getProduct(20, 4, Type.INSURANCE),
				getProduct(80, 5, Type.INSURANCE));
	}

	private Product getProduct(int price, int risk, Type income) {
		return new Product()
				.setId(UUID.randomUUID())
				.setPrice(new BigDecimal(price))
				.setRiskCategory(risk).setType(income);
	}

}