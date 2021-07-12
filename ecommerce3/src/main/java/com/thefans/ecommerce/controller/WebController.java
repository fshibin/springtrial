package com.thefans.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

   @RequestMapping(value = "/welcome")
   public ModelAndView welcome() {
      System.out.println("accessing /welcome...");
      return new ModelAndView("welcome");
   }
}

