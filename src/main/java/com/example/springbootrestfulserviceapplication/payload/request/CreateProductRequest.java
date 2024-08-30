package com.example.springbootrestfulserviceapplication.payload.request;

public record CreateProductRequest(
        String name,
        String description,
        String link,
        String owner,
        String contacts,
        int category_id
) {
}