package com.thefans.hystrixapp.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import com.thefans.hystrixapp.service.GreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class GreetingController {
 
    @Autowired
    private GreetingService greetingService;
 
    @GetMapping("/greeting/{username}")
    public String getGreeting(Model model, @PathVariable("username") String username) {
        model.addAttribute("greeting", greetingService.getGreeting(username));
        return "greeting";
    }
}
