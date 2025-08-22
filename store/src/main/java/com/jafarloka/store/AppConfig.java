package com.jafarloka.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AppConfig {

    @Value("${payment-gateway:paypal}")
    private String paymentGateway;

    @Bean
    public PaymentService stripe() {
        return new StripePaymentService();
    }

    @Bean
    public PaymentService paypal() {
        return new PaypalPaymentService();
    }

    @Bean
    @Lazy
    public OrderService orderService() {
        if(paymentGateway.equals("paypal")){
            return new OrderService(paypal());
        }
        return new OrderService(stripe());
    }

}
