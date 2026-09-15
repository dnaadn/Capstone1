package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {


    @NotEmpty(message = "Product ID is required")
    private String id;

    @NotEmpty(message = "Product Name is required")
    @Size(min = 3, message = "Minimum length of product name is 3 characters")
    private String name;

    @NotNull(message = "Product price is required")
    @Positive
    private double price;

    @NotEmpty(message = "Category ID is required")
    private String categoryID;
}
