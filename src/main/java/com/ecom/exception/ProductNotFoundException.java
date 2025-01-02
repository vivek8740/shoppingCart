package com.ecom.exception;

public class ProductNotFoundException extends RuntimeException {

    // Constructor that accepts a custom message
    public ProductNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a custom message and a cause (another exception)
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
