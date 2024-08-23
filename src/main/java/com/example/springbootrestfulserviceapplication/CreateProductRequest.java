package com.example.springbootrestfulserviceapplication;

public record CreateProductRequest(
        String name,
        String description,
        String link,
        String owner,
        String contacts
) {
}