package com.github.wojciechfiszer.micronautdemo.controller;

import com.github.wojciechfiszer.micronautdemo.service.User;
import com.github.wojciechfiszer.micronautdemo.service.UserNotFoundException;
import com.github.wojciechfiszer.micronautdemo.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Controller("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Get("/{id}")
    HttpResponse<User> getUser(String id) {
        log.debug("Received a request for a user with id {}", id);
        return HttpResponse.ok(userService.getUser(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Get
    HttpResponse<List<User>> getUsers() {
        log.debug("Received a request for users");
        return HttpResponse.ok(userService.getUsers());
    }

    @Post
    HttpResponse<User> createUser(@Body User user) {
        log.debug("Received a request to create a user {}", user);
        return HttpResponse.created(userService.createUser(user));
    }

    @Put("/{id}")
    HttpResponse<User> updateUser(String id, @Body User user) {
        user.setId(id);
        log.debug("Received a request to update a user {}", user);
        return HttpResponse.ok(userService.updateUser(user));
    }

    @Delete("/{id}")
    HttpResponse<?> deleteUser(String id) {
        userService.deleteUser(id);
        return HttpResponse.noContent();
    }
}
