I'll create a complete Spring Boot validation example with all the components you requested. This will be a comprehensive guide with code you can use directly.

## 1. Maven Dependencies (pom.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>validation-demo</artifactId>
    <version>1.0.0</version>
    <name>validation-demo</name>
    <description>Complete Spring Boot Validation Example</description>
    
    <properties>
        <java.version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Starter Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- Spring Boot Starter Validation - REQUIRED for validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- Lombok (optional, for cleaner code) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Spring Boot Starter Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 2. Nested Validation Models

### Address.java (Nested Object)
```java
package com.example.validationdemo.model;

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
```

### ContactInfo.java (Nested Object with Collection)
```java
package com.example.validationdemo.model;

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
```

### Department.java (Nested with Nested Lists)
```java
package com.example.validationdemo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Department {
    
    @NotBlank(message = "Department name is required")
    private String name;
    
    @NotBlank(message = "Department code is required")
    @Size(min = 2, max = 10, message = "Department code must be between 2 and 10 characters")
    private String code;
    
    // Nested validation for list of employees
    @NotEmpty(message = "At least one employee is required")
    @Valid
    private List<Employee> employees;
}
```

### Employee.java (Deeply Nested)
```java
package com.example.validationdemo.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Employee {
    
    @NotBlank(message = "Employee name is required")
    private String name;
    
    @NotBlank(message = "Employee ID is required")
    private String employeeId;
    
    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private BigDecimal salary;
    
    @Min(value = 18, message = "Employee must be at least 18 years old")
    private int age;
}
```

### CompanyRegistrationRequest.java (Main Request with Nested Objects)
```java
package com.example.validationdemo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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
```

## 3. Custom Validator (Bonus)

### StrongPassword.java (Annotation)
```java
package com.example.validationdemo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Password must be at least 8 characters long and contain uppercase, lowercase, digit, and special character";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

### StrongPasswordValidator.java
```java
package com.example.validationdemo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    
    private static final String PASSWORD_PATTERN = 
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return password.matches(PASSWORD_PATTERN);
    }
}
```

## 4. Global Exception Handler

```java
package com.example.validationdemo.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle @Valid validation errors for @RequestBody
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        
        log.error("Validation error: {}", ex.getMessage());
        
        Map<String, List<String>> errors = new HashMap<>();
        
        // Process field errors
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            
            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        });
        
        // Process global errors (object-level validation)
        ex.getBindingResult().getGlobalErrors().forEach(objectError -> {
            String objectName = objectError.getObjectName();
            String errorMessage = objectError.getDefaultMessage();
            errors.computeIfAbsent(objectName, k -> new ArrayList<>()).add(errorMessage);
        });
        
        // Build nested path for nested objects
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Map<String, List<String>> nestedErrors = fieldErrors.stream()
            .collect(Collectors.groupingBy(
                FieldError::getField,
                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
            ));
        
        ValidationErrorResponse response = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Request validation failed")
            .errors(errors)
            .path(getNestedPath(fieldErrors))
            .build();
        
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle @Validated parameter validation (e.g., @PathVariable, @RequestParam)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        
        log.error("Constraint violation: {}", ex.getMessage());
        
        Map<String, List<String>> errors = new HashMap<>();
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            // Extract just the field name from the path (e.g., "registerCompany.request.companyName" -> "companyName")
            String fieldName = propertyPath.contains(".") 
                ? propertyPath.substring(propertyPath.lastIndexOf(".") + 1) 
                : propertyPath;
            String message = violation.getMessage();
            
            errors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(message);
        }
        
        ValidationErrorResponse response = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Constraint Violation")
            .message("Parameter validation failed")
            .errors(errors)
            .build();
        
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle missing required parameters
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleMissingParams(
            MissingServletRequestParameterException ex) {
        
        log.error("Missing parameter: {}", ex.getParameterName());
        
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ex.getParameterName(), 
            Collections.singletonList("Parameter is required"));
        
        ValidationErrorResponse response = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Missing Parameter")
            .message("Required parameter '" + ex.getParameterName() + "' is not present")
            .errors(errors)
            .build();
        
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle type mismatch (e.g., passing string where number expected)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        
        log.error("Type mismatch: {}", ex.getName());
        
        Map<String, List<String>> errors = new HashMap<>();
        errors.put(ex.getName(), 
            Collections.singletonList("Invalid type. Expected " + 
                Objects.requireNonNull(ex.getRequiredType()).getSimpleName()));
        
        ValidationErrorResponse response = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Type Mismatch")
            .message("Invalid parameter type")
            .errors(errors)
            .build();
        
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ValidationErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error: ", ex);
        
        ValidationErrorResponse response = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message("An unexpected error occurred")
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Helper method to extract nested path information
     */
    private String getNestedPath(List<FieldError> fieldErrors) {
        if (fieldErrors.isEmpty()) return "";
        
        // Build path like "departments[0].employees[0].name"
        return fieldErrors.stream()
            .map(FieldError::getField)
            .findFirst()
            .orElse("");
    }
}
```

### ValidationErrorResponse.java (DTO)

```java
package com.example.validationdemo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, List<String>> errors;
}
```

## 5. REST Controller

```java
package com.example.validationdemo.controller;

import com.example.validationdemo.model.CompanyRegistrationRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/companies")
@Validated // Required for @PathVariable and @RequestParam validation
public class CompanyController {

    /**
     * POST endpoint with nested validation
     * @Valid triggers validation of the entire object graph including nested objects
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerCompany(
            @Valid @RequestBody CompanyRegistrationRequest request) {
        
        log.info("Registering company: {}", request.getCompanyName());
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Company registered successfully");
        response.put("companyName", request.getCompanyName());
        response.put("registrationNumber", request.getRegistrationNumber());
        response.put("departmentsCount", request.getDepartments().size());
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint with parameter validation
     */
    @GetMapping("/{companyId}")
    public ResponseEntity<Map<String, String>> getCompany(
            @PathVariable @Min(value = 1, message = "Company ID must be positive") Long companyId) {
        
        Map<String, String> response = new HashMap<>();
        response.put("companyId", companyId.toString());
        response.put("name", "Example Corp");
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint with request parameter validation
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, String>> searchCompanies(
            @RequestParam @NotBlank(message = "Search query is required") String query,
            @RequestParam(required = false, defaultValue = "10") 
            @Min(value = 1, message = "Page size must be at least 1") int pageSize) {
        
        Map<String, String> response = new HashMap<>();
        response.put("query", query);
        response.put("pageSize", String.valueOf(pageSize));
        
        return ResponseEntity.ok(response);
    }
}
```

## 6. Main Application Class

```java
package com.example.validationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidationDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ValidationDemoApplication.class, args);
    }
}
```

## 7. Example Test Requests

### Invalid Request (Multiple Nested Errors):

```bash
curl -X POST http://localhost:8080/api/companies/register \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "A",
    "registrationNumber": "INVALID",
    "foundedDate": "2025-01-01",
    "employeeCount": 0,
    "annualRevenue": -1000,
    "active": false,
    "address": {
      "street": "St",
      "city": "N",
      "zipCode": "bad",
      "country": ""
    },
    "contactInfo": {
      "email": "invalid-email",
      "phoneNumber": "123",
      "emergencyContactName": "",
      "emergencyContactPhone": "abc"
    },
    "departments": []
  }'
```

### Expected Error Response:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Failed",
  "message": "Request validation failed",
  "errors": {
    "companyName": ["Company name must be between 2 and 100 characters"],
    "registrationNumber": ["Registration number must be in format XX123456"],
    "foundedDate": ["Founded date must be in the past"],
    "employeeCount": ["Employee count must be at least 1"],
    "annualRevenue": ["Annual revenue must be positive"],
    "active": ["Company must be active to register"],
    "address.street": ["Street must be between 5 and 100 characters"],
    "address.city": ["City must be between 2 and 50 characters"],
    "address.zipCode": ["Zip code must be valid (e.g., 12345 or 12345-6789)"],
    "address.country": ["Country is required"],
    "contactInfo.email": ["Email must be valid"],
    "contactInfo.phoneNumber": ["Phone number must be valid"],
    "contactInfo.emergencyContactName": ["Emergency contact name is required"],
    "contactInfo.emergencyContactPhone": ["Emergency contact phone must be valid"],
    "departments": ["At least one department is required"]
  }
}
```

## Key Points to Remember:

1. **`spring-boot-starter-validation`** is the essential dependency
2. **`@Valid`** is required on nested objects AND on collections of objects to trigger validation
3. **`@Validated`** on the controller class enables `@PathVariable` and `@RequestParam` validation
4. **Exception handler** catches `MethodArgumentNotValidException` for body validation and `ConstraintViolationException` for parameter validation
5. **Nested paths** are automatically generated (e.g., `departments[0].employees[0].name`)

This example covers all common validation scenarios including deep nesting, collections, custom validators, and comprehensive error handling!