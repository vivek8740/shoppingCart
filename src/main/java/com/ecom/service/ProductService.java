package com.ecom.service;

import com.ecom.model.Product;
import java.util.List;

public interface ProductService {

    // Create or update a product
    Product saveProduct(Product product);

    // Get product by ID
    Product getProductById(int productId);

    // Get all products
    List<Product> getAllProducts();

    // Delete a product by ID
    boolean deleteProduct(int productId);

    Product updateProduct(Product product);

    List<Product> getAllActiveProducts();

    // Get products by category (optional, depending on your use case)
    //List<Product> getProductsByCategory(int categoryId);

    // Additional methods for other business logic can be added here
    
}

