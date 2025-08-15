package com.jafarloka.store;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("email")
@Primary
public class EmailNotificationService implements NotificationService{
    @Override
    public void send(String msg) {
        System.out.println("Sending via Email, with body: " + msg);
    }
}
