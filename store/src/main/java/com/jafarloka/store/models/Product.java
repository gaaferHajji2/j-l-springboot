package com.jafarloka.store.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Many Products belong to one Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    // @ManyToMany(mappedBy = "products")
    // @Builder.Default
    // private Set<User> users = new HashSet<>();

    // public void addUser(User user) {
    //     this.users.add(user);
    //     user.getProducts().add(this);
    // }

    // public void removeUser(User user) {
    //     this.users.remove(user);
    //     user.getProducts().remove(this);
    // }

}