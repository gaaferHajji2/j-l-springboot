package com.jafarloka.store;

public class OrderService {

    public void placeOrder() {
        var paymentService = new StripePaymentService();

        paymentService.processPayment(100_000);
    }

}
