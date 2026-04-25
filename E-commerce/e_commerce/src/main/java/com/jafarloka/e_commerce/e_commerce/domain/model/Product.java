package com.jafarloka.e_commerce.e_commerce.domain.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collation = "products")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @NotBlank(message = "Product name is required")
    @Size(max = 150)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    @NotBlank(message = "Category reference is required")
    private String categoryId;

    @Builder.Default
    private List<String> imageUrls = new ArrayList<>();

    @NotBlank(message = "SKU is required")
    @Pattern(regexp = "^[A-Z0-9-]{5,20}$", message = "SKU format invalid")
    private String sku;

    @Builder.Default
    private boolean active = true;
}
