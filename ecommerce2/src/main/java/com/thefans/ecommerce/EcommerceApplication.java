package com.thefans.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@SpringBootApplication
@RestController
public class EcommerceApplication extends SpringBootServletInitializer implements ApplicationRunner {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EcommerceApplication.class);
    }
    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        System.out.println("ApplicationRunner.run()");
        for (String op : arg0.getSourceArgs()) {
            System.out.println("Option: " + op);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
    @RequestMapping(value="/")
    public String hello() {
        return "Hello World from Shibin!";
    }
}
