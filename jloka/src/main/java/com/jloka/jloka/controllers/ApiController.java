package com.jloka.jloka.controllers;

import com.jloka.jloka.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private DataService dataService;
    
    @PostMapping("/create-sample")
    public ResponseEntity<String> createSampleData() {
        dataService.createSampleData();
        return ResponseEntity.ok("Sample data created successfully");
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUserInfo(@NonNull @PathVariable Long id) {
        // In a real application, you would return a DTO or entity
        dataService.displayUserData(id);
        return ResponseEntity.ok("User data displayed in console");
    }
}