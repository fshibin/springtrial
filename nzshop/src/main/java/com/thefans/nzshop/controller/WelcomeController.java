package com.thefans.nzshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class WelcomeController {

	@GetMapping(value="/")
	public String welcome() {
		return "Welcome to my shop!";
	}
}
