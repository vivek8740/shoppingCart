package com.ecom.service.serviceImpl;

import java.util.List;
import java.util.Optional;

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
        boolean isAvailabe = categoryRepositroy.existsByCategoryName(category.getCategoryName());
        if(!isAvailabe)
        return null;
        else{
            return categoryRepositroy.save(category);
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
}
