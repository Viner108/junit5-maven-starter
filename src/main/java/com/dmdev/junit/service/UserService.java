package com.dmdev.junit.service;

import com.dmdev.junit.dto.User;
import lombok.SneakyThrows;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class UserService {
    private final List<User> users=new ArrayList<>();
    public List<User> getAll() {
        return users;
    }

    public void add(User... users) {
        this.users.addAll(Arrays.asList(users));
    }

    public Optional<User> login(String username, String password) {
        if(username == null || password == null){
            throw new IllegalArgumentException("Username or password is null");
        }
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();
    }

    public Map<Integer, User> getAllConvertedById() {
        return users.stream()
                .collect(toMap(User::getId, Function.identity()));
    }
}
