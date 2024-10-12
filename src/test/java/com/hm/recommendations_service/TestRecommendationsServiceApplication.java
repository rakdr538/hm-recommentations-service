package com.hm.recommendations_service;

import org.springframework.boot.SpringApplication;

public class TestRecommendationsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(RecommendationsServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
