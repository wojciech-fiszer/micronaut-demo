package com.github.wojciechfiszer.micronautdemo.controller;

import com.github.wojciechfiszer.micronautdemo.service.User;
import com.github.wojciechfiszer.micronautdemo.service.UserNotFoundException;
import com.github.wojciechfiszer.micronautdemo.service.UserService;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class UserControllerTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    UserService userService;

    @MockBean(UserService.class)
    UserService userService() {
        return mock(UserService.class);
    }

    @Test
    void testGetUsersWhenUsersAreReturnedByUserServiceThenIsOk() {
        // given
        when(userService.getUsers()).thenReturn(List.of(User.builder().id("id").name("name").build()));

        // when
        HttpResponse<List<User>> response = client.toBlocking().exchange(
                HttpRequest.GET("/users"),
                Argument.listOf(User.class)
        );

        // then
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
    }

    @Test
    void testCreateUserWhenRequestBodyIsValidThenIsCreated() {
        // given
        User user = User.builder().id("id").name("name").build();
        when(userService.createUser(eq(user))).thenReturn(user);

        // when
        HttpResponse<User> response = client.toBlocking().exchange(HttpRequest.POST("/users", user), User.class);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(user, response.body());
    }

    @Test
    void testUpdateUserWhenRequestBodyIsValidThenIsOk() {
        // given
        User user = User.builder().id("id").name("name").build();
        when(userService.updateUser(eq(user))).thenReturn(user);

        // when
        HttpResponse<User> response = client.toBlocking().exchange(
                HttpRequest.PUT("/users/" + user.getId(), user),
                User.class
        );
        // then
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(user, response.body());
    }

    @Test
    void testUpdateUserWhenUserDoesNotExistThenIsNotFound() {
        // given
        User user = User.builder().id("id").name("name").build();
        when(userService.updateUser(eq(user))).thenThrow(new UserNotFoundException(user.getId()));

        // when
        HttpClientResponseException expectedException = null;
        try {
            client.toBlocking().exchange(
                    HttpRequest.PUT("/users/" + user.getId(), user),
                    User.class
            );
        } catch (HttpClientResponseException e) {
            expectedException = e;
        }

        // then
        assertNotNull(expectedException);
        assertEquals(HttpStatus.NOT_FOUND, expectedException.getStatus());
    }
}