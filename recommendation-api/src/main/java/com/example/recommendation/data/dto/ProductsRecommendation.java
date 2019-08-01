package com.example.recommendation.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductsRecommendation {

	private BigDecimal sum = BigDecimal.ZERO;
	private List<Product> products = new ArrayList<>();

	public void addProduct(Product product) {
		products.add(product);
		sum = sum.add(product.getPrice());
	}

}
