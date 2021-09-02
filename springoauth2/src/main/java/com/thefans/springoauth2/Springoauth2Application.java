package com.thefans.springoauth2;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Springoauth2Application extends WebSecurityConfigurerAdapter {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .logout(l -> l
                .logoutSuccessUrl("/").permitAll()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .csrf(c -> c
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .oauth2Login(o -> o
                .failureHandler((request, response, exception) -> {
        			request.getSession().setAttribute("error.message", exception.getMessage());
        			//handler.onAuthenticationFailure(request, response, exception);
                })
            );
        // @formatter:on
    }
    
    public static void main(String[] args) {
		SpringApplication.run(Springoauth2Application.class, args);
	}

}
