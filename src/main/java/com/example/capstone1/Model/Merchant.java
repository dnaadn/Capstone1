package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Merchant {


    @NotEmpty(message = "Merchant ID is required")
    private String id;

    @NotEmpty(message = "Merchant name is required")
    @Size(min = 3, message = "Minimum length of Merchant Name is 3 characters")
    private String name;
}
