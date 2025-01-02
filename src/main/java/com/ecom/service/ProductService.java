package com.ecom.service;

import com.ecom.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    // Create or update a product
    Product saveProduct(Product product);

    // Get product by ID
    Optional<Product> getProductById(int productId);

    // Get all products
    List<Product> getAllProducts();

    // Delete a product by ID
    void deleteProduct(int productId);

    // Get products by category (optional, depending on your use case)
    //List<Product> getProductsByCategory(int categoryId);

    // Additional methods for other business logic can be added here
    
}

