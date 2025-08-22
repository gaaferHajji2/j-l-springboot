package com.jafarloka.store;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void registerUser(User user) {
        User temp = userRepository.findByEmail(user.getEmail());

        if(temp != null) {
            System.out.println("Duplicate User with email: " + user.getEmail());
            return;
        }

        userRepository.save(user);

        notificationService.send("User Registred Successfully", user.getEmail());
    }

    @PostConstruct
    public void init() {
        System.out.println("UserService PostConstruct");
    }
}
