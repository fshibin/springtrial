package com.thefans.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocaleViewController {
   @RequestMapping("/locale")
   public String locale() {
      return "locale";
   }
}
