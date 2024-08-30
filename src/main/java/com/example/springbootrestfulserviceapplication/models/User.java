package com.example.springbootrestfulserviceapplication.models;


public record User(
        int id,
        String name,
        String email,
        String role,
        String password_hash
){
}
