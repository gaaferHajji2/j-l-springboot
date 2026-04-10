Here's a simple example demonstrating field validation in Spring Boot for your two cases:

## 1. User Model

```java
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class User {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
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
```

## 2. Profile Model with Nested User

```java
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Profile {
    @NotNull(message = "User is required")
    @Valid // This enables validation of the nested User object
    private User user;
    
    private String bio; // Optional field - no validation annotations
    
    // Constructors
    public Profile() {}
    
    public Profile(User user, String bio) {
        this.user = user;
        this.bio = bio;
    }
    
    // Getters and Setters
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
}
```

## Controller Example

```java
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Validated
public class ValidationController {
    
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        // If validation fails, Spring will automatically throw MethodArgumentNotValidException
        return ResponseEntity.ok("User created successfully");
    }
    
    @PostMapping("/profiles")
    public ResponseEntity<String> createProfile(@Valid @RequestBody Profile profile) {
        // This will validate both Profile and nested User fields
        return ResponseEntity.ok("Profile created successfully");
    }
}
```

## Global Exception Handler (Optional but Recommended)

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
```

## Example Requests

**Valid User Request:**
```json
{
  "email": "john@example.com",
  "website": "https://john.com"
}
```

**Invalid User Request (missing email):**
```json
{
  "website": "https://john.com"
}
```
Response: `{"email": "Email is required"}`

**Valid Profile Request:**
```json
{
  "user": {
    "email": "john@example.com",
    "website": "https://john.com"
  },
  "bio": "Software developer"
}
```

**Invalid Profile Request (nested validation):**
```json
{
  "user": {
    "email": "invalid-email",
    "website": "https://john.com"
  },
  "bio": "Software developer"
}
```
Response: `{"user.email": "Email should be valid"}`

## Dependencies

Make sure you have the validation dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

This setup provides:
- **Required field validation** for email using `@NotBlank`
- **Email format validation** using `@Email`
- **Nested object validation** using `@Valid` on the User field in Profile
- **Automatic validation** when using `@Valid` annotation on controller parameters
- **Clean error handling** with meaningful error messages