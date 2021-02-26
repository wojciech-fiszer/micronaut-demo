package com.github.wojciechfiszer.micronautdemo.service;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class User {
    private String id;
    private String name;
}
