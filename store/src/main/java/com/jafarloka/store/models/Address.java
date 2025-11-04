package com.jafarloka.store.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "street")
    private String street;
    @Column(nullable = false, name = "city")
    private String city;
    @Column(nullable = false, name = "zip")
    private String zip;
    @Column(nullable = false, name = "state")
    private String state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
