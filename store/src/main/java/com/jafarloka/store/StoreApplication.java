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
		var t1 = User.builder().email("test@test.com").password("Test@123").build();
		user.setName("Jafar Loka");
		user.setPassword("Test@123");
	}

}
