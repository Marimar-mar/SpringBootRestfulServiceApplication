package com.example.springbootrestfulserviceapplication;

public record Product(
        int id,
        String name,
        String description,
        String link,
        String owner,
        String contacts,
        int category_id
) {
}