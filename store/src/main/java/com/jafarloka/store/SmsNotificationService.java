package com.jafarloka.store;

import org.springframework.stereotype.Service;

@Service("sms")
public class SmsNotificationService implements NotificationService {
    @Override
    public void send(String msg) {
        System.out.println("Sending msg via sms, with body: " + msg);
    }
}
