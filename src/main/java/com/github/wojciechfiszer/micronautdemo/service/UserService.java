package com.github.wojciechfiszer.micronautdemo.service;

import com.github.wojciechfiszer.micronautdemo.persistance.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException(user.getId());
        }
        return userRepository.save(user);
    }
}
