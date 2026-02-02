package com.jloka.validation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.jloka.validation.entities.*;

@RestController
@RequestMapping("/api")
@Validated
public class TestController {
    
    @PostMapping("/create-user")
    public ResponseEntity<User> postMethodName(@Valid @RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create-profile")
    public ResponseEntity<Profile> postMethodName(@Valid @RequestBody Profile profile) {        
        return ResponseEntity.ok(profile);
    }
}