package com.jloka.validation.entities;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class User {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @URL(message = "Website should starts with http:// or https://")
    private String website; // Optional field - no validation annotations
    
    // Constructors
    public User() {}
    
    public User(String email, String website) {
        this.email = email;
        this.website = website;
    }
    
    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
}