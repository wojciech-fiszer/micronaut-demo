package com.github.wojciechfiszer.micronautdemo.controller;

import com.github.wojciechfiszer.micronautdemo.service.UserNotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {UserNotFoundException.class, ExceptionHandler.class})
public class UserNotFoundExceptionHandler implements ExceptionHandler<UserNotFoundException, HttpResponse<ErrorResponse>> {
    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, UserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        return HttpResponse.notFound(errorResponse);
    }
}
