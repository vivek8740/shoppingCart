package com.ecom.exception;

public class CategoryNotFoundException extends RuntimeException {

    // Constructor that accepts a custom message
    public CategoryNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a custom message and a cause (another exception)
    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
