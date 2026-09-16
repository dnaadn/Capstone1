package com.example.capstone1.Controller;

import com.example.capstone1.API.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStock(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStock();
        return ResponseEntity.status(200).body(merchantStocks);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isAdded = merchantStockService.addMerchantStock(merchantStock);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock could not be added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id,@RequestBody @Valid MerchantStock merchantStock, Errors errors ){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }

        boolean isUpdated = merchantStockService.updateStock(id,merchantStock);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        boolean isDeleted = merchantStockService.deleteStock(id);

        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant stock deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Merchant stock not found"));
    }

//endPoint 11
    @PutMapping("/add-stock/{productid}/{merchantid}/{amount}")
    public ResponseEntity<?> addStock(@PathVariable String productid, @PathVariable String merchantid, @PathVariable int amount) {

        boolean isAdded = merchantStockService.addStock(productid, merchantid, amount);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("Stock added successfully"));
        }

        return ResponseEntity.status(400).body(new ApiResponse("Stock could not be added"));
    }

//endPoint 12
    @PostMapping("/buy-product/{userid}/{productid}/{merchantid}")
    public ResponseEntity<?> buyProduct(@PathVariable String userid, @PathVariable String productid, @PathVariable String merchantid) {

        String isBought = merchantStockService.buyProduct(userid, productid, merchantid);
        if (isBought.equals("Product bought successfully")) {
            return ResponseEntity.status(200).body(new ApiResponse(isBought));
        }
        return ResponseEntity.status(400).body(new ApiResponse(isBought));
    }

//extra endPoint
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getMerchantProduct(@PathVariable String productId){
        ArrayList<MerchantStock> productResult = merchantStockService.getMerchantStocksByProduct(productId);
        if(productResult == null) {
            return ResponseEntity.status(404).body(new ApiResponse("Product not found"));
        }
        return ResponseEntity.status(200).body(productResult);
    }

//extra endPoint
    @GetMapping("/lowstock-price/{productid}/{merchantid}")
    public ResponseEntity<?> getLowStockDiscountedPrice(@PathVariable String productid, @PathVariable String merchantid){
        Double price = merchantStockService.getLowStockDiscountedPrice(productid, merchantid);

        if (price == null) {
            return ResponseEntity.status(404) .body(new ApiResponse("Product or Merchant not found"));
        }
        return ResponseEntity.status(200).body(price);
    }

 //extra endPoint
    @GetMapping("/stock-status/{productId}")
    public ResponseEntity<?> getProductStockStatus(@PathVariable String productId) {
        String result = merchantStockService.getProductStockStatus(productId);

        if (result == null) {
            return ResponseEntity.status(404).body(new ApiResponse("No stock record found for this product"));
        }
        return ResponseEntity.status(200).body(result);
    }


//extra endPoint
@GetMapping("/out-of-stock/{merchantId}")
public ResponseEntity<?> getOutOfStockProducts(@PathVariable String merchantId) {

    return ResponseEntity.ok(
            merchantStockService.getOutOfStockProducts(merchantId)
    );
}







}
