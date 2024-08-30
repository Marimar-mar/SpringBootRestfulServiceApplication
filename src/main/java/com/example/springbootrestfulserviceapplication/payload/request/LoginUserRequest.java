package com.example.springbootrestfulserviceapplication.payload.request;

public record LoginUserRequest(
            String email,
            String password
    ){
    }