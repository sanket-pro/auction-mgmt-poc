package com.auction.bid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RESTConfig {
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
