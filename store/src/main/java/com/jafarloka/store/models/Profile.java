package com.jafarloka.store.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "bio")
    private String bio;
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
    @Column(nullable = false, name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(nullable = false, name = "loyalty_points")
    private int loyaltyPoints;
}
