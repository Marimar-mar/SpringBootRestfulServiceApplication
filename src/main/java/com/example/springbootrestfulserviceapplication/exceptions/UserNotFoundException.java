package com.example.springbootrestfulserviceapplication.exceptions;

public class UserNotFoundException extends RuntimeException {

    private final String userName;

    public UserNotFoundException(String userName) {
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        return "User with name = " + userName + " not found";
    }
}