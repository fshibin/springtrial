package com.thefans.hystrixapp;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCircuitBreaker
@SpringBootApplication
public class HystrixappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixappApplication.class, args);
	}

}
