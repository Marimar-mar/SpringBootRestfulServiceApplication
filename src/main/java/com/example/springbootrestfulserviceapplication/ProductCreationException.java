package com.example.springbootrestfulserviceapplication;

public class ProductCreationException extends RuntimeException {
    public ProductCreationException(String message) {
        super(message);
    }
}