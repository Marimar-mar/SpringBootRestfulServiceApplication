package com.example.springbootrestfulserviceapplication.security.services;

import com.example.springbootrestfulserviceapplication.models.User;
import com.example.springbootrestfulserviceapplication.payload.request.RegisterUserRequest;

import java.util.Optional;

public interface UserService {

    User registerUser(RegisterUserRequest request);

    User findByEmail(String email);

    User findByName(String name);

    User findById(int Id);

}
