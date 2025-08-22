package com.jafarloka.store;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Component
@Lazy
public class OrderService {

    private PaymentService paymentService;

    public OrderService() {
        System.out.println("Default Constructor");
    }

    // @Autowired
//    public OrderService(@Qualifier("paypal") PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }

    @PostConstruct
    public void init() {
        System.out.println("OrderService PostConstruct");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("OrderService PreDestroyed");
    }

    public OrderService(PaymentService paymentService) {
        System.out.println("OrderService created");
        this.paymentService = paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.processPayment(100_000);
    }

}
