package com.jloka.validation.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Address {
    
    @NotBlank(message = "Street is required")
    @Size(min = 5, max = 100, message = "Street must be between 5 and 100 characters")
    private String street;
    
    @NotBlank(message = "City is required")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters")
    private String city;
    
    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zip code must be valid (e.g., 12345 or 12345-6789)")
    private String zipCode;
    
    @NotBlank(message = "Country is required")
    private String country;
}