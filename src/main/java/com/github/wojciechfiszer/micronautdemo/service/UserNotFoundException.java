package com.github.wojciechfiszer.micronautdemo.service;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("User with id " + id + " was not found");
    }
}
