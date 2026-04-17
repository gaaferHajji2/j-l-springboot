package com.jloka.validation.models;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Profile {
    @NotNull(message = "User can't be null")
    @Valid
    private User user;

    @Size(min = 1, max= 5000)
    @Nullable
    private String bio;

    // Constructors
    public Profile() {}
    
    public Profile(User user, String bio) {
        this.user = user;
        this.bio = bio;
    }
}