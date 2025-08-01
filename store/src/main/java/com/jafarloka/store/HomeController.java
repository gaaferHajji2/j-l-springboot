package com.jafarloka.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${jloka.value}")
    private String value;

    @RequestMapping("/")
    public String index() {

        System.out.println("The app name is: " + appName + ", The value is: " + value);

        return "index.html";
    }

    @RequestMapping("/order")
    public String order(){
        OrderService orderService = new OrderService(new StripePaymentService());
        orderService.placeOrder();

        orderService = new OrderService(new PaypalPaymentService());
        orderService.placeOrder();

        return "order.html";
    }

}
