package com.github.wojciechfiszer.micronautdemo.persistance;

import com.github.wojciechfiszer.micronautdemo.service.User;

import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> byId = new ConcurrentHashMap<>();

    @Override
    public boolean existsById(String id) {
        return byId.containsKey(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(byId.get(id));
    }

    @Override
    public List<User> findAll() {
        return List.of(byId.values().toArray(new User[]{}));
    }

    @Override
    public User save(User user) {
        String id = user.getId();
        if (id == null) {
            id = UUID.randomUUID().toString();
            user.setId(id);
        }
        byId.put(id, user);
        return user;
    }
}
