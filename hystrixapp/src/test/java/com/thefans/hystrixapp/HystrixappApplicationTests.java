package com.thefans.hystrixapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootTest
@EnableCircuitBreaker
class HystrixappApplicationTests {

	@Test
	void contextLoads() {
	}

}
