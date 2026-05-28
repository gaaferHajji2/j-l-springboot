package com.jafarloka.store.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor // we define it because jpa expected no args constructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false, name = "email")
    private String email;
    @Column(nullable = false, name = "password")
    private String password;
    @OneToMany(mappedBy = "user") // this is the field in address-entity
    @Builder.Default
    // private final List<Address> addresses = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    @ManyToMany
    @JoinTable(
        name = "user_tags",
        joinColumns = @JoinColumn(name= "user_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }
    
    public void addTag(Tag tag){
        tags.add(tag);
        tag.getUsers().add(this);
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
        tag.getUsers().remove(this);
    }
}
