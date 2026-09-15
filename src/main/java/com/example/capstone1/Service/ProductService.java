package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final CategoryService categoryService;
    //private final MerchantStockService merchantStockService;

    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProduct(){
        return products;
    }

    public boolean addProduct(Product product){
        //Does the category id exist?
        boolean categoryExists = false;
        for(Category category: categoryService.getCategories()){
            if(category.getId().equals(product.getCategoryID())){
                categoryExists = true;
                break;
            }
        }
        if(!categoryExists){
            return false;
        }
        //check id-uniqueness,
        for(Product existing: products){
            if(existing.getId().equals(product.getId())){
                return false;
            }
        }
        products.add(product);
        return true;
    }


    public boolean updateProduct(String id, Product updateProduct){
        for(int i =0; i<products.size(); i++){
            if(products.get(i).getId().equals(id)){
                boolean categoryExists = false;
                for(Category category: categoryService.getCategories()){
                    if(category.getId().equals(updateProduct.getCategoryID())){
                        categoryExists = true;
                        break;
                    }
                }
                if(!categoryExists){
                    return false;
                }
                updateProduct.setId(id);
                products.set(i,updateProduct);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for(int i = 0; i<products.size(); i++){
            if(products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }

// extra endPoint #5 to filter a product by price range
    public ArrayList<Product> filterByPriceRange(double minPrice, double maxPrice){
        ArrayList<Product> result = new ArrayList<>();

        for(Product product: products){
            if(product.getPrice()>= minPrice && product.getPrice()<= maxPrice){
                result.add(product);
            }
        }
        return result;
    }




}
