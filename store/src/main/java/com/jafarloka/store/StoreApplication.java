package com.jafarloka.store;

import com.jafarloka.store.models.Address;
import com.jafarloka.store.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

		var user = new User();
		var t1 = User.builder().email("test@test.com").password("Test@123").build();
		user.setName("Jafar Loka");
		user.setPassword("Test@123");

		var addr1 = Address.builder().zip("test@123").state("test@123").street("test@123").build();
		var addr2 = Address.builder().zip("test@123").state("test@123").street("test@123").build();

//		user.setAddresses(List.of(addr1, addr2));
//		addr1.setUser(user);
//		addr2.setUser(user);

		user.addAddress(addr1);
		user.addAddress(addr2);
	}

}
