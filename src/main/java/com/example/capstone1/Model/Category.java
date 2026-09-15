package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {


    @NotEmpty(message = "Category ID is required")
    private String id;

    @NotEmpty(message = "Category name is required")
    @Size(min = 3, message = "Minimum length of Category Name is 3 characters")
    private String name;
}
