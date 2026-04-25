package com.jafarloka.e_commerce.e_commerce.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.jafarloka.e_commerce.e_commerce.domain.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collation = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password hash is required")
    private String passwordHash;

    @NotNull(message = "User role is required")
    private UserRole role;

    @Builder.Default
    private boolean enabled = true;

}
