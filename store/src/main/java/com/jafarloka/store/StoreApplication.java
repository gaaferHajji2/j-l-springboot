package com.jafarloka.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jafarloka.store.NotificationManager;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);

//		var resource = context.getBean(HeavyResource.class);

		 OrderService orderService = context.getBean(OrderService.class);
		// orderService.placeOrder();

		context.close();
//
//		var notificationManager = context.getBean(NotificationManager.class);
//
//		notificationManager.sendNotification("Hi, My Name ==> Jafar Loka");
	}

}
