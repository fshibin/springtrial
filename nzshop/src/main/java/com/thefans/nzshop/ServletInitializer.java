package com.thefans.nzshop;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NzshopApplication.class);
	}

	void add(String a) {}
	int add() {return 0;}
	
	public ServletInitializer(int q) {}
	public static void main(String[] args0) {
		//ServletInitializer s = new ServletInitializer();
	}
}
