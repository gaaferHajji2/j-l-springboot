Here are the production-ready enums for your e-commerce domain, structured exactly as requested. I've included **only the necessary methods** and provided a complete reference table of every annotation with its fully qualified package.

### 📁 Package Structure
```
src/main/java/com/ecommerce/domain/enums/
├── UserRole.java
├── OrderStatus.java
└── PaymentStatus.java
```

---

### 1️⃣ `UserRole.java`
```java
package com.ecommerce.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    CUSTOMER("CUSTOMER", "Standard customer"),
    VENDOR("VENDOR", "Product seller or vendor"),
    ADMIN("ADMIN", "System administrator");

    private final String code;
    private final String displayName;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserRole fromCode(String code) {
        for (UserRole role : values()) {
            if (role.code.equalsIgnoreCase(code.trim())) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid user role: " + code);
    }
}
```

---

### 2️⃣ `OrderStatus.java`
```java
package com.ecommerce.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING("PENDING", "Order placed, awaiting confirmation"),
    CONFIRMED("CONFIRMED", "Order confirmed by system/vendor"),
    PROCESSING("PROCESSING", "Order is being prepared"),
    SHIPPED("SHIPPED", "Order dispatched to customer"),
    DELIVERED("DELIVERED", "Order successfully delivered"),
    CANCELLED("CANCELLED", "Order cancelled by user or admin"),
    REFUNDED("REFUNDED", "Payment refunded");

    private final String code;
    private final String displayName;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderStatus fromCode(String code) {
        for (OrderStatus status : values()) {
            if (status.code.equalsIgnoreCase(code.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid order status: " + code);
    }
}
```

---

### 3️⃣ `PaymentStatus.java`
```java
package com.ecommerce.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("PENDING", "Awaiting payment"),
    PAID("PAID", "Payment completed successfully"),
    FAILED("FAILED", "Payment transaction failed"),
    REFUNDED("REFUNDED", "Payment refunded");

    private final String code;
    private final String displayName;

    @JsonValue
    public String getCode() {
        return code;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static PaymentStatus fromCode(String code) {
        for (PaymentStatus status : values()) {
            if (status.code.equalsIgnoreCase(code.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid payment status: " + code);
    }
}
```

---

### 📦 Annotation Package Reference Table

| Enum | Annotation | Fully Qualified Package |
|------|------------|--------------------------|
| All | `@Getter` | `lombok.Getter` |
| All | `@RequiredArgsConstructor` | `lombok.RequiredArgsConstructor` |
| All | `@JsonValue` | `com.fasterxml.jackson.annotation.JsonValue` |
| All | `@JsonCreator(mode = ...)` | `com.fasterxml.jackson.annotation.JsonCreator` |
| `UserRole` | (class/package) | `com.ecommerce.domain.enums` |
| `OrderStatus` | (class/package) | `com.ecommerce.domain.enums` |
| `PaymentStatus` | (class/package) | `com.ecommerce.domain.enums` |

---

### 🔍 Why These Methods Are Included (Only If Required)

| Method/Annotation | Purpose | Required? |
|-------------------|---------|-----------|
| `@JsonValue` on `getCode()` | Ensures Jackson serializes the enum as `"CUSTOMER"` instead of `"customer"` or `{"code":"CUSTOMER","displayName":"..."}`. Keeps API payloads clean. | ✅ Yes (for consistent REST responses) |
| `@JsonCreator(mode = DELEGATING)` | Allows Jackson to safely deserialize incoming strings like `"pending"` or `"CUSTOMER"` into the correct enum constant, ignoring case/whitespace. Prevents `400 Bad Request` on typo inputs. | ✅ Yes (for robust API deserialization) |
| `fromCode(String)` | Used in service layer or query params where you need programmatic, case-insensitive enum resolution. Throws `IllegalArgumentException` for invalid states (fails fast). | ✅ Yes (business logic safety) |
| `code` & `displayName` fields | `code` is for DB/API consistency. `displayName` is for UI/admin panels or audit logs without hardcoding strings elsewhere. | ✅ Yes (separation of concerns) |

### 💡 Professional Notes
1. **No `toString()` Override Needed**: Jackson uses `@JsonValue`, so `toString()` remains the default enum `name()` (safe for logging).
2. **MongoDB Storage**: Spring Data MongoDB automatically stores enums as strings. With `@JsonValue`, they'll persist exactly as `"ADMIN"`, `"PENDING"`, etc.
3. **Validation Integration**: You can use these enums directly in DTOs:
   ```java
   @NotNull(message = "Role is required")
   private UserRole role;
   ```
   Jackson will automatically validate against `fromCode()` during deserialization.
4. **Extensibility**: If you add payment gateways later (e.g., `STRIPE_PAID`), just add new constants. The `fromCode()` method scales automatically.

Let me know if you need the **JWT Security Filter & Config**, **Global Validation Exception Handler**, or **MongoDB Repository/Service implementations** next.