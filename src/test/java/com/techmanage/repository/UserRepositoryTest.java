package com.techmanage.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.techmanage.entity.User;
import com.techmanage.entity.UserType;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                          LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua Teste, 123");
    }

    @Test
    void testSaveUser() {
        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("João Silva", savedUser.getFullName());
        assertEquals("joao@email.com", savedUser.getEmail());
        assertEquals("+5511999999999", savedUser.getPhone());
        assertEquals(LocalDate.of(1990, 5, 15), savedUser.getBirthDate());
        assertEquals(UserType.ADMIN, savedUser.getUserType());
        assertEquals("Rua Teste, 123", savedUser.getAddress());
    }

    @Test
    void testFindById() {
        User savedUser = userRepository.save(testUser);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("João Silva", foundUser.get().getFullName());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<User> foundUser = userRepository.findById(999L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindAll() {
        User user1 = userRepository.save(testUser);
        User user2 = new User("Maria Santos", "maria@email.com", "+5511888888888",
                             LocalDate.of(1985, 8, 20), UserType.EDITOR, "Rua Teste, 123");
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getFullName().equals("João Silva")));
        assertTrue(users.stream().anyMatch(u -> u.getFullName().equals("Maria Santos")));
    }

    @Test
    void testFindAllEmpty() {
        List<User> users = userRepository.findAll();

        assertTrue(users.isEmpty());
    }

    @Test
    void testDeleteUser() {
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.getId();

        userRepository.delete(savedUser);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testDeleteById() {
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.getId();

        userRepository.deleteById(userId);

        Optional<User> deletedUser = userRepository.findById(userId);
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testUpdateUser() {
        User savedUser = userRepository.save(testUser);

        savedUser.setFullName("João Santos");
        savedUser.setEmail("joao.santos@email.com");
        savedUser.setUserType(UserType.EDITOR);

        User updatedUser = userRepository.save(savedUser);

        assertEquals("João Santos", updatedUser.getFullName());
        assertEquals("joao.santos@email.com", updatedUser.getEmail());
        assertEquals(UserType.EDITOR, updatedUser.getUserType());
    }

    @Test
    void testCount() {
        assertEquals(0, userRepository.count());

        userRepository.save(testUser);
        assertEquals(1, userRepository.count());

        User user2 = new User("Maria Santos", "maria@email.com", "+5511888888888",
                             LocalDate.of(1985, 8, 20), UserType.EDITOR, "Rua Teste, 123");
        userRepository.save(user2);
        assertEquals(2, userRepository.count());
    }

    @Test
    void testExistsById() {
        assertFalse(userRepository.existsById(999L));

        User savedUser = userRepository.save(testUser);
        assertTrue(userRepository.existsById(savedUser.getId()));
    }

    @Test
    void testDeleteAll() {
        userRepository.save(testUser);
        User user2 = new User("Maria Santos", "maria@email.com", "+5511888888888",
                             LocalDate.of(1985, 8, 20), UserType.EDITOR, "Rua Teste, 123");
        userRepository.save(user2);

        assertEquals(2, userRepository.count());

        userRepository.deleteAll();

        assertEquals(0, userRepository.count());
        assertTrue(userRepository.findAll().isEmpty());
    }
}