package com.github.wojciechfiszer.micronautdemo.persistance;

import com.github.wojciechfiszer.micronautdemo.service.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean existsById(String id);
    Optional<User> findById(String id);
    List<User> findAll();
    User save(User user);
}
