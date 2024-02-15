package com.dmdev.junit.service;

import com.dmdev.junit.dto.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {
    @Test
    void userEmptyIfNoUserAdded(){
        UserService userService = new UserService();
        List<User> all = userService.getAll();
        assertTrue(all.isEmpty());
    }
}
