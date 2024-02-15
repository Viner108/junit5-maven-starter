package com.dmdev.junit.service;

import com.dmdev.junit.dto.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private UserService userService;
    @BeforeAll
     void init(){
        System.out.println("Before all: "+this);
    }
    @BeforeEach
    void prepare(){
        System.out.println("Before each: "+this);
        userService=new UserService();
    }
    @Test
    void userEmptyIfNoUserAdded(){
        System.out.println("Test 1: "+this);
        List<User> all = userService.getAll();
        assertTrue(all.isEmpty());
    }

    @Test
    void usersSizeIfUserAdded(){
        System.out.println("Test 2: "+this);
        userService.add(new User());
        userService.add(new User());

        List<User> users= userService.getAll();
        assertEquals(2,users.size());
    }
    @AfterEach
    void deleteDataFromDatabase(){
        System.out.println("After each: "+this);
    }
    @AfterAll
     void closeConnectionPool(){
        System.out.println("After all: "+this);
    }
}
