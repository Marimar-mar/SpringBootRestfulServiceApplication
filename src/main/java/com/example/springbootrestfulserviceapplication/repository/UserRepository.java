package com.example.springbootrestfulserviceapplication.repository;

import com.example.springbootrestfulserviceapplication.models.User;

import java.util.Optional;

public interface UserRepository {//за взаимодействие с бд Users
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
    Optional<User> findById(int UserId);
    Optional<User> save(User name);
}