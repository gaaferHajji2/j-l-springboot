Here’s a production-ready, Clean Architecture-aligned set of models with validation for a Spring Boot 3+ / MongoDB E-commerce application. I'll structure them to separate **Domain Models** (persistence-focused) from **DTOs** (API validation-focused), as recommended in Clean Architecture.

### 📦 Recommended Package Structure
```
com.ecommerce
├── domain/
│   ├── model/          (MongoDB Documents & Value Objects)
│   └── enums/          (Role, OrderStatus, etc.)
├── application/
│   ├── dto/            (Request/Response DTOs with @Valid)
│   └── service/        (Business logic)
├── infrastructure/
│   └── repository/     (MongoDB Repositories)
├── presentation/
│   └── controller/     (REST Endpoints + @Validated)
└── security/
    └── jwt/            (Token generation, claims, filter)
```

---

### 1️⃣ Domain Models (MongoDB + Base Validation)
*Note: Domain models contain safety constraints. Heavy API validation belongs in DTOs.*

#### `BaseEntity.java`
```java
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    private String id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
```

#### `User.java`
```java
@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
```

#### `Category.java`
```java
@Document(collection = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
```

#### `Product.java`
```java
@Document(collection = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
```

#### `Order.java` & `OrderItem.java`
```java
@Document(collection = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order extends BaseEntity {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItem> items;

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be positive")
    private BigDecimal totalAmount;

    @Valid // Enables nested validation
    private ShippingAddress shippingAddress;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @DecimalMin(value = "0.01", message = "Item price must be positive")
    private BigDecimal priceAtPurchase;
}

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ShippingAddress {
    @NotBlank private String street;
    @NotBlank private String city;
    @NotBlank private String state;
    @NotBlank @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$") private String zipCode;
    @NotBlank private String country;
}
```

---

### 2️⃣ Request DTOs (API Validation Layer)
*Clean Architecture dictates: Validate at the boundary, map to domain.*

#### `ProductRequestDto.java`
```java
public record ProductRequestDto(
    @NotBlank @Size(max = 150) String name,
    @Size(max = 1000) String description,
    @NotNull @DecimalMin("0.01") BigDecimal price,
    @NotNull @Min(0) Integer stock,
    @NotBlank String categoryId,
    List<String> imageUrls,
    @NotBlank @Pattern(regexp = "^[A-Z0-9-]{5,20}$") String sku
) {}
```

#### `CreateOrderRequestDto.java`
```java
public record CreateOrderRequestDto(
    @NotEmpty List<OrderItemRequestDto> items,
    @Valid @NotNull ShippingAddress shippingAddress
) {}

public record OrderItemRequestDto(
    @NotBlank String productId,
    @NotNull @Min(1) Integer quantity
) {}
```

---

### 3️⃣ MongoDB Population & Pagination Strategy
Since MongoDB doesn't support joins, populate relations manually in the service layer:

```java
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    // Pagination + Sorting
    public Page<Product> getProducts(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, 
            Sort.by(Sort.Direction.fromString(direction), sortBy));
        return productRepo.findAll(pageable);
    }

    // Populate relations on ID fetch
    public ProductWithRelationsDto getProductById(String id) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        Category category = categoryRepo.findById(product.getCategoryId())
            .orElse(null); // Handle gracefully

        return ProductWithRelationsDto.builder()
            .product(product)
            .category(category)
            .build();
    }
}
```

---

### 4️⃣ JWT Token Structure
When generating tokens, embed required claims for RBAC & user context:
```java
public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId())           // Standard JWT sub = User ID
        .claim("username", user.getUsername())
        .claim("email", user.getEmail())
        .claim("role", user.getRole().name())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
}
```
*Extract in `OncePerRequestFilter` → store in `SecurityContext` → use `@AuthenticationPrincipal` in controllers.*

---

### 🛠 Professional Best Practices

| Area | Recommendation |
|------|----------------|
| **Validation** | Use DTOs with `@Valid` in controllers. Domain models get lightweight safety constraints. Enable global validation with `@RestControllerAdvice` + `MethodArgumentNotValidException` handler. |
| **MongoDB Indexing** | Add `@Indexed(unique = true)` on `email`, `username`, `slug`, `sku`. Use compound indexes for pagination queries. |
| **Auditing** | Enable with `@EnableMongoAuditing` in a `@Configuration` class. MongoDB handles `createdAt`/`updatedAt` automatically. |
| **Pagination** | Return `Page<T>` from repositories. Expose `Pageable` parameters in controllers. Never return full collections. |
| **Error Handling** | Use RFC 7807 `ProblemDetail` responses. Map validation errors to structured JSON. |
| **Security** | Hash passwords with `BCryptPasswordEncoder`. Validate JWT signature, expiration, and issuer. Use `@PreAuthorize("hasRole('ADMIN')")` where needed. |
| **Transactions** | MongoDB supports multi-document transactions via `@Transactional` on Spring Data Mongo 3.1+. Use for Order creation + stock deduction. |
| **Caching** | Cache categories/products with `@Cacheable`. Invalidate on updates. MongoDB reads are fast, but caching reduces DB load for hot endpoints. |

### 🔌 Required Dependencies (pom.xml)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.5</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

Let me know if you want the **JWT Security Config**, **Global Exception Handler for Validation**, or **Repository/Service implementations** next.