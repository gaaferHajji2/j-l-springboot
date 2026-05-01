package com.jafarloka.store;

import com.jafarloka.store.models.Address;
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

		var addr1 = Address.builder().zip("test@123").state("test@123").street("test@123").build();
		var addr2 = Address.builder().zip("test@123").state("test@123").street("test@123").build();

		user.addAddress(addr1);
		user.addAddress(addr2);
	}
}
