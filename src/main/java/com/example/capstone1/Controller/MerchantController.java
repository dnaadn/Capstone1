package com.example.capstone1.Controller;

import com.example.capstone1.API.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchant(){
        ArrayList<Merchant> merchants = merchantService.getMerchants();
        return ResponseEntity.status(200).body(merchants);
    }


    @PostMapping("add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAdded = merchantService.addMerchant(merchant);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant could not be added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant could not be updated"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        boolean isDeleted = merchantService.deleteMerchant(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));

    }


}
