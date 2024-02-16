package com.dmdev.junit.service;

import com.dmdev.junit.dto.User;
import org.hamcrest.collection.IsEmptyCollection;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hamcrest.MatcherAssert;
import org.assertj.core.api.Assertions;

import static org.hamcrest.collection.IsEmptyCollection.*;
import static org.junit.jupiter.api.Assertions.*;
@Tag("fast")
@Tag("user")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    private UserService userService;
    private static final User IVAN = User.of(1, "Ivan", "123");
    private static final User PETR = User.of(2, "Petr", "111");

    @BeforeAll
    void init() {
        System.out.println("Before all: " + this);
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        userService = new UserService();
    }

    @Test
    void userEmptyIfNoUserAdded() {
        System.out.println("Test 1: " + this);
        List<User> all = userService.getAll();
        assertTrue(all.isEmpty());
    }

    @Test
    void usersSizeIfUserAdded() {
        System.out.println("Test 2: " + this);
        userService.add(IVAN);
        userService.add(PETR);

        List<User> users = userService.getAll();

        MatcherAssert.assertThat(users, empty());
        Assertions.assertThat(users).hasSize(2);
//        assertEquals(2,users.size());
    }

    @Test
    @Tag("login")
    void loginSuccessIfUserExists() {
        userService.add(IVAN);
        Optional<User> maybeUser = userService.login(IVAN.getUsername(), IVAN.getPassword());

        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> Assertions.assertThat(user).isEqualTo(IVAN));
//        maybeUser.ifPresent(user -> assertEquals(IVAN,user));
    }

    @Test
    void throwExceptionIfUsernameOrPasswordIsNull() {
        assertAll(
                () -> {
                    IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.login(null, "dummy"));
                    Assertions.assertThat(e.getMessage()).isEqualTo("Username or password is null");
                },
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login("dummy", null))
        );

    }

    @Test
    void usersConvertedToMapById() {
        userService.add(IVAN, PETR);

        Map<Integer, User> users = userService.getAllConvertedById();

        MatcherAssert.assertThat(users, IsMapContaining.hasKey(IVAN.getId()));
        assertAll(
                () -> Assertions.assertThat(users).containsKeys(IVAN.getId(), PETR.getId()),
                () -> Assertions.assertThat(users).containsValues(IVAN, PETR)
        );
    }

    @Test
    @Tag("login")
    void loginFailIfPasswordIsNotCorrect() {
        userService.add(IVAN);

        Optional<User> maybeUser = userService.login(IVAN.getUsername(), "sfsv");

        assertTrue(maybeUser.isEmpty());
    }

    @Test
    @Tag("login")
    void loginFailIfUserDoesNotExist() {
        userService.add(IVAN);

        Optional<User> maybeUser = userService.login("ghfjdk", IVAN.getPassword());

        assertTrue(maybeUser.isEmpty());
    }

    @AfterEach
    void deleteDataFromDatabase() {
        System.out.println("After each: " + this);
    }

    @AfterAll
    void closeConnectionPool() {
        System.out.println("After all: " + this);
    }
}
