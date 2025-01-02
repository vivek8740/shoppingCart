package com.ecom.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    //List<Product> findByCategory_CategoryId(int categoryId);

}
