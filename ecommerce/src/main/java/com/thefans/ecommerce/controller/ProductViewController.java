package com.thefans.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductViewController {
   @RequestMapping("/view-products")
   public String viewProducts() {
      return "view-products";
   }
   @RequestMapping("/add-products")
   public String addProducts() {
      return "add-products";   
   }   
}
