package com.ecom.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    // Custom query method to find all active products 
    List<Product> findByIsActiveTrue();

    // Custom query method to find active products by category
    List<Product> findByCategoryAndIsActiveTrue(String category);

}
