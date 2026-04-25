Here is the exact folder/package structure for each model and DTO, followed by a complete reference table of every annotation used with its **fully qualified package name** (aligned with **Spring Boot 3+ / Jakarta EE**).

---

### 📁 Exact Folder & Package Structure
```
src/main/java/com/ecommerce/
├── domain/
│   ├── model/
│   │   ├── BaseEntity.java
│   │   ├── User.java
│   │   ├── Category.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   └── ShippingAddress.java
│   └── enums/
│       ├── UserRole.java
│       ├── OrderStatus.java
│       └── PaymentStatus.java
└── application/
    └── dto/
        ├── ProductRequestDto.java
        ├── CreateOrderRequestDto.java
        ├── OrderItemRequestDto.java
        └── ProductWithRelationsDto.java
```

---

### 📦 Annotation Reference Table

| File / Component | Annotation | Fully Qualified Package |
|------------------|------------|--------------------------|
| **`BaseEntity.java`** | `@MappedSuperclass` | `jakarta.persistence.MappedSuperclass` |
| | `@Id` | `org.springframework.data.annotation.Id` |
| | `@CreatedDate` | `org.springframework.data.annotation.CreatedDate` |
| | `@LastModifiedDate` | `org.springframework.data.annotation.LastModifiedDate` |
| | `@Getter`, `@Setter` | `lombok.Getter`, `lombok.Setter` |
| **`User.java`** | `@Document(collection = "users")` | `org.springframework.data.mongodb.core.mapping.Document` |
| | `@NotBlank`, `@Size`, `@Email`, `@NotNull` | `jakarta.validation.constraints.*` |
| | Lombok: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder` | `lombok.*` |
| **`Category.java`** | `@Document(collection = "categories")` | `org.springframework.data.mongodb.core.mapping.Document` |
| | `@NotBlank`, `@Size`, `@Pattern` | `jakarta.validation.constraints.*` |
| | Lombok annotations | `lombok.*` |
| **`Product.java`** | `@Document(collection = "products")` | `org.springframework.data.mongodb.core.mapping.Document` |
| | `@NotBlank`, `@Size`, `@NotNull`, `@DecimalMin`, `@Min`, `@Pattern` | `jakarta.validation.constraints.*` |
| | `@Builder.Default` | `lombok.Builder.Default` |
| | Other Lombok | `lombok.*` |
| **`Order.java`** | `@Document(collection = "orders")` | `org.springframework.data.mongodb.core.mapping.Document` |
| | `@NotEmpty`, `@NotNull`, `@DecimalMin` | `jakarta.validation.constraints.*` |
| | `@Valid` | `jakarta.validation.Valid` |
| | Lombok annotations | `lombok.*` |
| **`OrderItem.java`** | `@NotBlank`, `@NotNull`, `@Min`, `@DecimalMin` | `jakarta.validation.constraints.*` |
| | Lombok annotations | `lombok.*` |
| **`ShippingAddress.java`** | `@NotBlank`, `@Pattern` | `jakarta.validation.constraints.*` |
| | Lombok annotations | `lombok.*` |
| **DTOs (`application/dto/`)** | Record validation (`@NotBlank`, `@Size`, `@NotNull`, `@Min`, `@DecimalMin`, `@Pattern`, `@NotEmpty`) | `jakarta.validation.constraints.*` |
| | `@Valid` (for nested validation) | `jakarta.validation.Valid` |
| **Service/Config (Reference)** | `@Service` | `org.springframework.stereotype.Service` |
| | `@RequiredArgsConstructor` | `lombok.RequiredArgsConstructor` |
| | `@EnableMongoAuditing` | `org.springframework.data.mongodb.config.EnableMongoAuditing` |
| | `@Transactional` | `org.springframework.transaction.annotation.Transactional` |
| | `@PreAuthorize` | `org.springframework.security.access.prepost.PreAuthorize` |
| | `@Cacheable` | `org.springframework.cache.annotation.Cacheable` |
| | `@RestControllerAdvice`, `@ExceptionHandler` | `org.springframework.web.bind.annotation.*` |

---

### ⚠️ Important Configuration Notes

1. **Jakarta EE Migration**: Spring Boot 3+ uses `jakarta.*` instead of `javax.*`. Ensure your `pom.xml` uses Spring Boot 3.2+ or higher.
2. **Lombok Setup**: Add `lombok` to `pom.xml` and enable annotation processing in your IDE.
3. **MongoDB Auditing**: `@CreatedDate` & `@LastModifiedDate` **require** `@EnableMongoAuditing` on a configuration class:
   ```java
   @Configuration
   @EnableMongoAuditing
   public class MongoConfig {}
   ```
4. **Validation Trigger**: Annotations on DTOs/Controllers only work if you add `@Valid` or `@Validated` on controller method parameters:
   ```java
   @PostMapping("/products")
   public ResponseEntity<Product> create(@Valid @RequestBody ProductRequestDto dto) { ... }
   ```
5. **`@MappedSuperclass` in MongoDB**: While technically from JPA, Spring Data MongoDB supports it for inheritance mapping. If you prefer pure MongoDB mapping, you can replace it with a plain `abstract class` and move `@Id`/auditing fields directly into child entities or use `@Document` inheritance.

Let me know if you need the **Global Exception Handler for validation errors**, **JWT Filter & Security Config**, or **Repository/Service implementation** next.