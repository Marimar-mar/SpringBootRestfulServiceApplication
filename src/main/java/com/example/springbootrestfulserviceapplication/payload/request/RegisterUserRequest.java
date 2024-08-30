package com.example.springbootrestfulserviceapplication.payload.request;

public record RegisterUserRequest (
        String name,
        String email,
        String password
    ) {
    }