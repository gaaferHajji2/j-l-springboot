package com.jafarloka.store.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

}
