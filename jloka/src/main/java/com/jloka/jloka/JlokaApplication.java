package com.jloka.jloka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@SpringBootApplication
@RestController
public class JlokaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JlokaApplication.class, args);
	}

	@GetMapping("/hello")
	public String getMethodName(@RequestParam(name="name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
}
