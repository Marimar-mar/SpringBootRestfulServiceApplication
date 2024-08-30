package com.example.springbootrestfulserviceapplication.exceptions;

public class NoProductsFoundException extends RuntimeException {
    public NoProductsFoundException(String message) {
        super(message);
    }
}