package com.example.recommendation.client.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class HostConfig {

	@Value("${clients.profile.host}")
	private String hostProfile;

	@Value("${clients.product.host}")
	private String hostProduct;
}
