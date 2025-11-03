package com.jafarloka.store;

import com.jafarloka.store.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

		var user = new User();
		user.setName("Jafar Loka");
		user.setPassword("Test@123");
	}

}
