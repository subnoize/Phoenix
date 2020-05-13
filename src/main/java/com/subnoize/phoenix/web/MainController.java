package com.subnoize.phoenix.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/home")
	public String home() {
		return "home.html";
	}

	@RequestMapping("/login")
	public String login() {
		return "login.html";
	}
}
