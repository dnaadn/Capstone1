package com.example.capstone1.Controller;

import com.example.capstone1.API.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        ArrayList<Product> products = productService.getProduct();
        return ResponseEntity.status(200).body(products);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isAdded = productService.addProduct(product);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product could not be added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = productService.updateProduct(id, product);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product could not be updated"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        boolean isDeleted = productService.deleteProduct(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Product not found"));

    }

//extra endPoint
    @GetMapping("/price-range/{minPrice}/{maxPrice}")
    public ResponseEntity<?> filterByPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice) {
        ArrayList<Product> products = productService.filterByPriceRange(minPrice,maxPrice);
            return ResponseEntity.status(200).body(products);
    }





}
