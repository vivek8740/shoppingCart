package com.ecom.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return categoryRepositroy.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryRepositroy.delete(categoryRepositroy.findById(id).get());
    }

    @Override
    public Boolean isCategoryExist(String categoryName) {
        return categoryRepositroy.existsByCategoryName(categoryName);
    }

}
