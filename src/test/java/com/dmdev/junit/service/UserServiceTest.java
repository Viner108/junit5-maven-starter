package com.dmdev.junit.service;

import com.dmdev.junit.dto.User;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private UserService userService;
    private static final User IVAN=User.of(1, "Ivan", "123");
    private static final User PETR=User.of(2, "Petr", "111");
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
    void loginSuccessIfUserExists(){
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getUsername(),IVAN.getPassword());

        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals(IVAN,user));
    }
    @Test
    void loginFailIfPasswordIsNotCorrect(){
        userService.add(IVAN);

        Optional<User> maybeUser = userService.login(IVAN.getUsername(),"sfsv");

        assertTrue(maybeUser.isEmpty());
    }

    @Test
    void loginFailIfUserDoesNotExist(){
        userService.add(IVAN);

        Optional<User> maybeUser = userService.login("ghfjdk",IVAN.getPassword());

        assertTrue(maybeUser.isEmpty());
    }


    @Test
    void usersSizeIfUserAdded(){
        System.out.println("Test 2: "+this);
        userService.add(IVAN);
        userService.add(PETR);

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
