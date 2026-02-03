package com.jloka.validation.models;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRegistrationRequest {
    
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    private String companyName;
    
    @NotBlank(message = "Registration number is required")
    @Pattern(regexp = "^[A-Z]{2}\\d{6}$", message = "Registration number must be in format XX123456")
    private String registrationNumber;
    
    @NotNull(message = "Founded date is required")
    @Past(message = "Founded date must be in the past")
    private LocalDate foundedDate;
    
    @Min(value = 1, message = "Employee count must be at least 1")
    @Max(value = 100000, message = "Employee count cannot exceed 100,000")
    private int employeeCount;
    
    @NotNull(message = "Annual revenue is required")
    @Positive(message = "Annual revenue must be positive")
    private Double annualRevenue;
    
    // Nested object validation - @Valid is REQUIRED
    @NotNull(message = "Address is required")
    @Valid
    private Address address;
    
    // Nested object validation
    @NotNull(message = "Contact information is required")
    @Valid
    private ContactInfo contactInfo;
    
    // Nested list validation - @Valid validates each item in the list
    @NotEmpty(message = "At least one department is required")
    @Valid
    private List<Department> departments;
    
    // Optional nested object (no @NotNull, but validated if present)
    @Valid
    private Address billingAddress;
    
    @AssertTrue(message = "Company must be active to register")
    private boolean active;
}
