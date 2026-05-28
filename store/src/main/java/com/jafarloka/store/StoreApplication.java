package com.jafarloka.store;

import com.jafarloka.store.models.Address;
import com.jafarloka.store.models.Tag;
import com.jafarloka.store.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);

		var user = new User();
		user.setName("Jafar Loka");
		user.setPassword("Test@123");

		Tag tag1 = new Tag("JLoka-01");
		Tag tag2 = new Tag("JLoka-02");

		user.addTag(tag1);
		user.addTag(tag2);
		
		System.out.println("The user is: " + user);
	}
}
