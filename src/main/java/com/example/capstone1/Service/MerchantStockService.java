package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    private final MerchantService merchantService;
    private final ProductService productService;
    private final UserService userService;

    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStock(){
        return merchantStocks;
    }


    public boolean addMerchantStock(MerchantStock merchantStock){

        //check if the product exist, to check the product id
        boolean productExist = false;
        for(Product product: productService.getProduct()){
            if(product.getId().equals(merchantStock.getProductid())){
                productExist = true;
                break;
            }
        }
        if(!productExist){
            return false;
        }
        // check if merchant exist, to check merchan id
        boolean merchantExist = false;
        for(Merchant merchant: merchantService.getMerchants()){
            if(merchant.getId().equals(merchantStock.getMerchantid())){
                merchantExist= true;
                break;
            }
        }
        if (!merchantExist){
            return false;
        }
        // stock must be more than 10 at start
        if(merchantStock.getStock()<=10){
            return false;
        }
        // id must be unique
        for(MerchantStock existing: merchantStocks){
            if(existing.getId().equals(merchantStock.getId())){
                return false;
            }
        }
        merchantStocks.add(merchantStock);
        return true;
    }


    public boolean updateStock(String id, MerchantStock merchantStock){
        for(int i =0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getId().equals(id)){
                //check product id
                boolean productExists = false;
                for(Product product: productService.getProduct()){
                    if(product.getId().equals(merchantStock.getProductid())){
                        productExists = true;
                        break;
                    }
                }
                if(!productExists){
                    return false;
                }
                boolean merchantExists = false;
                for(Merchant merchant: merchantService.getMerchants()){
                    if(merchant.getId().equals(merchantStock.getMerchantid())){
                        merchantExists = true;
                        break;
                    }
                }
                if(!merchantExists){
                    return false;
                }
                if(merchantStock.getStock()<=10){
                    return false;
                }
                merchantStock.setId(id);
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }


    public boolean deleteStock(String id){
        for(int i =0; i<merchantStocks.size(); i++){
            if(merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    // endpoint to add more stock to a merchant stock
    public boolean addStock(String productid, String merchantid, int amount){
        if(amount<=0){
            return false;
        }
        for(MerchantStock stock: merchantStocks){
            if(stock.getProductid().equals(productid) && stock.getMerchantid().equals(merchantid)){
                stock.setStock(stock.getStock()+amount);
                return true;
            }
        }
        return false;
    }


    //endpoint where user can buy a product directly
    public String buyProduct(String userid, String productid, String merchantid){
        User user = null;
        Product product = null;
        MerchantStock stock = null;
        // check if user exists
        for(User u : userService.getUsers()){
            if(u.getId().equals(userid)){
                user = u;
                break;
            }
        }
        // check if product exists
        for(Product p : productService.getProduct()){
            if(p.getId().equals(productid)){
                product = p;
                break;
            }
        }
        // check if merchant stock exists
        for(MerchantStock s : merchantStocks){
            if(s.getProductid().equals(productid) &&
                    s.getMerchantid().equals(merchantid)){
                stock = s;
                break;
            }
        }
        // check if user, product, and stock exist
        if(user == null){
            return "User not found";
        }
        if(product == null){
            return "Product not found";
        }
        if(stock == null){
            return "Product is not available from this merchant";
        }
        // check if product is out of stock
        if(stock.getStock() <= 0){
            return "Product is out of stock";
        }
        // check if user has enough balance
        if(user.getBalance() < product.getPrice()){
            return "Insufficient balance";
        }
        // reduce stock
        stock.setStock(stock.getStock() - 1);
        // deduct product price from user balance
        user.setBalance(user.getBalance() - product.getPrice());

        return "Product bought successfully";
    }

    //extra endPoint#1 to view the stock and price of a product across different merchants
    public ArrayList<MerchantStock> getMerchantStocksByProduct(String productid){
        boolean productExists = false;
     //search for the product that has tha same id in parameter, if it exists>true, doesn't exist>false and return null
        for (Product p : productService.getProduct()) {
            if (p.getId().equals(productid)) {
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            return null;
        }
//if the product exists, then search for it in all merchant stocks and return the ones they have stock
        ArrayList<MerchantStock> result = new ArrayList<>();
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductid().equals(productid) && stock.getStock() > 0) {
                result.add(stock);
            }
        }
        return result;
    }

    //extra endPoint#2 ,Low-stock products receive an automatic discount to encourage sales before inventory runs out
    private static final double LowStockDiscount = 0.10;
    private static final int LowStockLimit = 5;

    public Double getLowStockDiscountedPrice(String productId, String merchantId) {

        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductid().equals(productId) && stock.getMerchantid().equals(merchantId)) {
                Product product = null;
                for (Product p : productService.getProduct()) {
                    if (p.getId().equals(productId)) {
                        product = p;
                        break;
                    }
                }
                if (product == null) {
                    return null;
                }
                if (stock.getStock() > 0 && stock.getStock() <= LowStockLimit) {
                    return product.getPrice() * (1 - LowStockDiscount);
                }
                return product.getPrice();
            }
        }
        return null;
    }



//extra endPoint#3
    public String getProductStockStatus(String productId) {

        boolean productExists = false;
        for (Product p : productService.getProduct()) {
            if (p.getId().equals(productId)) {
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            return null; // product not found
        }
        int totalStock = 0;
        for (MerchantStock stock : merchantStocks) {
            if (stock.getProductid().equals(productId)) {
                totalStock += stock.getStock();
            }
        }
        if (totalStock == 0) {
            return "Out of Stock";
        }
        if (totalStock <= 10) {
            return "Low Stock";
        }
        return "Available";
    }



    // extra endPoint #4 to view products that are out of stock
    public ArrayList<Product> getOutOfStockProducts(String merchantId) {

        ArrayList<Product> result = new ArrayList<>();

        for (Product product : productService.getProduct()) {

            for (MerchantStock stock : merchantStocks) {

                if (stock.getMerchantid().equals(merchantId)
                        && stock.getProductid().equals(product.getId())
                        && stock.getStock() == 0) {

                    result.add(product);
                    break;
                }
            }
        }

        return result;
    }


}
