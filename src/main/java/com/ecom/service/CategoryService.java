package com.ecom.service;

import java.util.List;

import com.ecom.model.Category;

public interface CategoryService {

    //Basic methods
     Category createCategory(Category category);
     Category getCategoryById(Integer id);
     List<Category> getAllCategories();
     Category updateCategory(Category category);
     Boolean deleteCategory(int id);

     //Utility Methods
     Boolean isCategoryExist(String categoryName);
     Category findCatetoryById(int id);

}
