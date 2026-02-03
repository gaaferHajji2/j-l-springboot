package com.jloka.validation.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ContactInfo {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid")
    private String phoneNumber;
    
    @NotBlank(message = "Emergency contact name is required")
    private String emergencyContactName;
    
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Emergency contact phone must be valid")
    private String emergencyContactPhone;
}