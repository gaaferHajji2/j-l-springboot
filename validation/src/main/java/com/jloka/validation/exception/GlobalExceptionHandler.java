package com.jloka.validation.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

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
        // Map<String, List<String>> nestedErrors = fieldErrors.stream()
        //     .collect(Collectors.groupingBy(
        //         FieldError::getField,
        //         Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
        //     ));

        // System.out.println("The HashMap Of nested Errors: " + nestedErrors);
        
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