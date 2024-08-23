package com.example.springbootrestfulserviceapplication;

public class ProductNotFoundException extends RuntimeException {

    private final int productId;

    public ProductNotFoundException(int productId) {
        this.productId = productId;
    }

    @Override
    public String getMessage() {
        return "Product with id = " + productId + " not found";
    }
}