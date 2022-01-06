package com.example.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(value="/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(value="/Login")
	public static String Welcome() {
		return "Mobile \n" +
				"Login Client \n" +
				"Login Costumer";
	}



}
