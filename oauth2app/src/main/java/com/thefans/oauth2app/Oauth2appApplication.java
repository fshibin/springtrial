package com.thefans.oauth2app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuthorizationServer
public class Oauth2appApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2appApplication.class, args);
	}

}
