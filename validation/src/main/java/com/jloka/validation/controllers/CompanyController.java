package com.jloka.validation.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jloka.validation.models.CompanyRegistrationRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

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
