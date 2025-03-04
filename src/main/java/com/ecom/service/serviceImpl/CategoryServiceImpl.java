package com.ecom.service.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ecom.model.Category;
import com.ecom.repository.CategoryRepositroy;
import com.ecom.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepositroy categoryRepositroy;

    @Override
    public Category createCategory(Category category) {
        return categoryRepositroy.save(category);
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepositroy.findById(id).get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepositroy.findAll();
    }

    @Override
    public Category updateCategory(Category category) {
        Category isAvailabe = categoryRepositroy.findById(category.getCategoryId()).get();
        if(ObjectUtils.isEmpty(isAvailabe))
        return null;
        else{
            Category dbCategory = categoryRepositroy.findById(category.getCategoryId()).get();
            dbCategory.setCategoryName(category.getCategoryName());
            dbCategory.setImageName(category.getImageName());
            dbCategory.setIsActive(category.getIsActive());
            return categoryRepositroy.save(dbCategory);
        }
    }

    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepositroy.findById(id).orElse(null);

        if(!ObjectUtils.isEmpty(category)){
            categoryRepositroy.delete(categoryRepositroy.findById(id).get());
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Boolean isCategoryExist(String categoryName) {
        return categoryRepositroy.existsByCategoryName(categoryName);
    }

    @Override
    public Category findCatetoryById(int id) {
        Category category = categoryRepositroy.findById(id).orElse(null);
        return category;
    }

    @Override
    public List<Category> getActiveCategories() {
        List<Category> activeCategories = categoryRepositroy.findByIsActiveTrue();
        return activeCategories;
    }
}
