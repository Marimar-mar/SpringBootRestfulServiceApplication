package com.example.springbootrestfulserviceapplication.exceptions;

public class ProductCreationException extends RuntimeException {
    public ProductCreationException(String message) {
        super(message);
    }
}