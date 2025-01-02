package com.ecom.service.serviceImpl;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;
import com.ecom.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        // Validate the product before saving
        validateProduct(product);

        // Check if the category exists

        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error saving product. Data integrity violation: " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> getProductById(int productId) {
        // Validate productId
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        return productRepository.findById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean deleteProduct(int productId) {
        // Validate productId
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

        productRepository.deleteById(productId);

        return true;
    }

    // Helper method to check if a category exists
    private boolean categoryExists(int categoryId) {
        // Assuming you have a CategoryRepository, you can inject it here
        // For now, just returning true for simplicity
        return true; // This should query your Category repository to check if the category exists
    }

    // Helper method for validating product fields
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }

        // Title validation: must not be null or empty
        if (product.getTitle() == null || product.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Product title must not be empty");
        }

        // Price validation: must be greater than 0
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than 0");
        }

        // Stock validation: must be greater than or equal to 0 (can be zero)
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }

        // Category validation: category must be non-null
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("Product must have a valid category");
        }
    }
}
