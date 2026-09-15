package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "User ID is required")
    private String id;

    @NotEmpty
    @Size(min = 5, message = "Minimum length of User name is 5 characters")
    private String username;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{7,}$")
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "(?i)^(Admin|Customer)$")
    private String role;

    @NotNull
    @Positive
    private double balance;

}


