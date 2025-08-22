package com.jafarloka.store;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
public class NotificationManager {
    private final NotificationService notificationService;

    public NotificationManager(NotificationService notificationService) {
        System.out.println("NotificationManager created");
        this.notificationService = notificationService;
    }

    public void sendNotification(String msg) {
        this.notificationService.send(msg);
    }
}
