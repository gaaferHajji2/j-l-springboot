package com.jafarloka.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jafarloka.store.NotificationManager;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

//		var resource = context.getBean(HeavyResource.class);

//		OrderService orderService = context.getBean(OrderService.class);
//		orderService.placeOrder();
//
//		var notificationManager = context.getBean(NotificationManager.class);
//
//		notificationManager.sendNotification("Hi, My Name ==> Jafar Loka");
	}

}
