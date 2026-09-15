package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category>  categories = new ArrayList<>();

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public boolean addCategory(Category category){
        for(Category category1: categories){
            if(category1.getId().equals(category.getId())){
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public boolean updateCategory(String id, Category updatedCategory){
        for(int i =0; i<categories.size(); i++){
            if(categories.get(i).getId().equals(id)){
                updatedCategory.setId(id);
                categories.set(i,updatedCategory);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        for(int i =0; i<categories.size(); i++){
            if(categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }


}
