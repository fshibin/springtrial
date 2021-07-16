package com.thefans.flywayapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class HystrixController {
 
    @GetMapping("/hystrix/{username}")
    public String greeting(@PathVariable("username") String username) {
        if ("Alice".equalsIgnoreCase(username)) {
        	try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        if ("Bob".equalsIgnoreCase(username)) {
            throw new RuntimeException("Mocked exception when username is Bob");
        }
        return String.format("Hello %s!\n", username);
    }
}
