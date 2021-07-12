package com.thefans.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
   @RequestMapping(value = {"/", "/welcome"})
   public String index() {
      System.out.println("accessing /welcome...");
      return "welcome";
   }
}
