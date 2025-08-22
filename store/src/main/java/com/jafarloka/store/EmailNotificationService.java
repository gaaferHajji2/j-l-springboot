package com.jafarloka.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Service("email")
//@Primary
public class EmailNotificationService implements NotificationService {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private String port;

    @Override
    public void send(String msg, String recipientEmail) {
        System.out.println("Sending via Email, with body: " + msg + ", to " + recipientEmail);

        System.out.println("The host is: " + host);
        System.out.println("The port is: " + port);
    }
}
