package com.example.springbootrestfulserviceapplication.exceptions;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}