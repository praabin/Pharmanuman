package com.pharmanuman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	
	@RequestMapping("/about")
	public String about() {
		System.out.println("about");
		return "about";
	}

	@RequestMapping("/home")
	public String home() {
		System.out.println("home");
		return "home";
	}

	@RequestMapping("/signup")
	public String singin() {
		System.out.println("signup");
		return "signup";
	}

}
