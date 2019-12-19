package com.repositories;

import com.domains.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    //UserRepository.findByLogin() with exist login returns correct user
    @Test
    void findByLoginWithExistLoginReturnsCorrectUser() {
        String existLogin = "exist";
        User user = new User(existLogin);
        Optional<User> expected = Optional.of(user);
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> actual = repository.findByLogin(existLogin);

        assertEquals(expected, actual);
    }


    //UserRepository.findByLogin() with nonexistent login returns empty Optional
    @Test
    void findByLoginWithNonexistentLoginReturnsEmptyOptional() {
        String nonexistentLogin = "nonexistent";

        Optional<User> actual = repository.findByLogin(nonexistentLogin);
        assertFalse(actual.isPresent());
    }

    //users with duplicate login can not be added
    @Test
    void addingUsersWithDuplicateLogin() {
        String login = "login";
        User userOne = new User(login);
        User userTwo = new User(login);
        entityManager.persist(userOne);
        entityManager.persist(userTwo);

        assertThrows(RuntimeException.class, () -> entityManager.flush());
    }
}