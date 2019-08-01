package com.example.recommendation.service.impl;

import com.example.recommendation.client.impl.ProductClient;
import com.example.recommendation.client.impl.ProfileClient;
import com.example.recommendation.data.dto.Product;
import com.example.recommendation.data.dto.ProductsRecommendation;
import com.example.recommendation.data.dto.Profile;
import com.example.recommendation.data.dto.Type;
import com.example.recommendation.service.RecommendationService;
import com.example.recommendation.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

	private final ProfileClient profileClient;
	private final ProductClient productClient;

	private BiPredicate<Integer, Integer> riskFilter = (profileRisk, productRisk) -> profileRisk >= productRisk;

	@Override
	public Mono<ProductsRecommendation> get(String profileId, BigDecimal sum) {
		Mono<Profile> profileMono = profileClient.get(profileId);
		validationProfile(profileMono, profileId);
		Mono<List<Product>> pr = profileMono.flatMap(profile -> productClient.getAll()
				.filter(product -> riskFilter.test(profile.getRisk(), product.getRiskCategory()))
				.collectList());
		return pr.map(products -> filterProducts(products, sum));
	}

	private void validationProfile(Mono<Profile> profileMono, String profileId) {
		if (Objects.isNull(profileMono)) {
			throw new DataNotFoundException("Could not find profile: " + profileId);
		}
	}

	private ProductsRecommendation filterProducts(List<Product> products, BigDecimal sum) {

		Map<Type, List<Product>> productType = products.stream()
				.collect(Collectors.groupingBy(Product::getType, HashMap::new, Collectors.toCollection(ArrayList::new)));

		List<List<Product>> incomeCombinations = getCombinations(productType.get(Type.INCOME));
		List<List<Product>> insuranceCombitation = getCombinations(productType.get(Type.INSURANCE));

		List<ProductsRecommendation> PRIncome = getProductsRecommendations(incomeCombinations);
		List<ProductsRecommendation> PRInsurance = getProductsRecommendations(insuranceCombitation);

		return getPRUnited(sum, PRIncome, PRInsurance);
	}

	private List<List<Product>> getCombinations(List<Product> products) {
		List<List<Product>> incomeCombinations = new LinkedList<>();
		for (int i = 1; i <= products.size(); i++) {
			incomeCombinations.addAll(combination(products, i));
		}
		return incomeCombinations;
	}

	private ProductsRecommendation definePR(List<Product> products) {
		return new ProductsRecommendation()
				.setProducts(products)
				.setSum(products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, ArithmeticUtils::add));
	}

	private <T> List<List<T>> combination(List<T> values, int size) {

		if (0 == size) {
			return Collections.singletonList(Collections.emptyList());
		}

		if (values.isEmpty()) {
			return Collections.emptyList();
		}

		List<List<T>> combination = new LinkedList<>();

		T actual = values.iterator().next();

		List<T> subSet = new LinkedList<T>(values);
		subSet.remove(actual);

		List<List<T>> subSetCombination = combination(subSet, size - 1);

		for (List<T> set : subSetCombination) {
			List<T> newSet = new LinkedList<T>(set);
			newSet.add(0, actual);
			combination.add(newSet);
		}

		combination.addAll(combination(subSet, size));

		return combination;
	}

	private List<ProductsRecommendation> getProductsRecommendations(List<List<Product>> productCombination) {
		return productCombination.stream()
				.map(this::definePR)
				.sorted(((o1, o2) -> o2.getSum().compareTo(o1.getSum())))
				.collect(Collectors.toList());
	}

	private ProductsRecommendation getPRUnited(BigDecimal sum, List<ProductsRecommendation> PRIncome, List<ProductsRecommendation> PRInsurance) {
		ProductsRecommendation result = new ProductsRecommendation();
		for (ProductsRecommendation prInc : PRIncome) {
			BigDecimal prSumInc = prInc.getSum();
			for (ProductsRecommendation prIns : PRInsurance) {
				BigDecimal prSumIns = prIns.getSum();
				if (prSumInc.compareTo(prSumIns) == 0) {
					BigDecimal packSum = prSumInc.add(prSumIns);
					BigDecimal allSum = packSum.add(packSum);
					BigDecimal resSum = result.getSum().add(allSum);
					if (resSum.compareTo(sum) <= 0) {
						prInc.getProducts().forEach(result::addProduct);
						prIns.getProducts().forEach(result::addProduct);
						break;
					}
				}
			}
		}
		return result;
	}
}
