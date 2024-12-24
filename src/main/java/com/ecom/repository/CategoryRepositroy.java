package com.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Category;

public interface CategoryRepositroy extends JpaRepository<Category,Integer>{

    Boolean existsByCategoryName(String categoryName);

}
