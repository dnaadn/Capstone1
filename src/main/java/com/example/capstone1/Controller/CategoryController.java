package com.example.capstone1.Controller;

import com.example.capstone1.API.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories(){
        ArrayList<Category> categories = categoryService.getCategories();
        return ResponseEntity.status(200).body(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAdded= categoryService.addCategory(category);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Category added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category could not be added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = categoryService.updateCategory(id,category);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Category could not be updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("Category not found"));

    }
}
