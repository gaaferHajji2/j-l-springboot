package com.jloka.validation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jloka.validation.models.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@Validated
public class TestController {
    
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create-profile")
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody Profile profile) {        
        return ResponseEntity.ok(profile);
    }
}