package com.github.wojciechfiszer.micronautdemo.service;

import com.github.wojciechfiszer.micronautdemo.persistance.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        checkIfUserExists(user.getId());
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        checkIfUserExists(id);
        userRepository.deleteById(id);
    }

    private void checkIfUserExists(String id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
    }
}
