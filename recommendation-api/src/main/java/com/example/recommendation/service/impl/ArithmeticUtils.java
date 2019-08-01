package com.example.recommendation.service.impl;

import java.math.BigDecimal;

class ArithmeticUtils {

	static BigDecimal add(BigDecimal bigDecimal, BigDecimal augend) {
		bigDecimal = bigDecimal.add(augend);
		return bigDecimal;
	}
}
