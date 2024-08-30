package com.example.springbootrestfulserviceapplication.models;

public record Product(
        int id,
        String name,
        String description,
        String link,
        String owner,
        String contacts,
        int category_id,
        String status
) {
}