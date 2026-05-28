package com.jafarloka.store.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // Maps to the userId field in the composite key
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId") // Maps to the productId field in the composite key
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
