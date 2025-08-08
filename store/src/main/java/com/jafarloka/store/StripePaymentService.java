package com.jafarloka.store;

import org.springframework.stereotype.Service;

@Service
public class StripePaymentService implements PaymentService {

    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe");
        System.out.println("amount: " + amount + " processed");
    }

}
