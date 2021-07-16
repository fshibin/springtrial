package com.thefans.hystrixapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class GreetingService {
    @HystrixCommand(fallbackMethod = "defaultGreeting")
    public String getGreeting(String username) {
        return new RestTemplate().getForObject(
            "http://localhost:8080/hystrix/{username}", 
            String.class, username
        );
    }
 
    @SuppressWarnings("unused")
	private String defaultGreeting(String username) {
        return "Hi there, this is a fallback!";
    }
}
