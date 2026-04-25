package com.jafarloka.e_commerce.e_commerce.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "categories")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    private String slug;

    private String parentCategoryId; // Self-reference
}

