package com.techmanage.service;

import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.exception.EmailAlreadyExistsException;
import com.techmanage.exception.PhoneAlreadyExistsException;
import com.techmanage.exception.UserNotFoundException;
import com.techmanage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private User existingUser;

    @BeforeEach
    void setUp() {
        user = new User("João Silva", "joao@email.com", "+5511999999999",
                       LocalDate.of(1990, 5, 15), UserType.ADMIN);
        user.setId(1L);

        existingUser = new User("Maria Santos", "maria@email.com", "+5511888888888",
                               LocalDate.of(1985, 8, 20), UserType.EDITOR);
        existingUser.setId(2L);
    }

    @Test
    void createUser_Success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("João Silva", result.getFullName());
        assertEquals("joao@email.com", result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_EmailAlreadyExists() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(existingUser));

        User newUser = new User("Outro Nome", "maria@email.com", "+5511777777777",
                               LocalDate.of(1995, 3, 10), UserType.VIEWER);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.createUser(newUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getAllUsers_Success() {
        List<User> users = Arrays.asList(user, existingUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.contains(user));
        assertTrue(result.contains(existingUser));
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva", result.getFullName());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }

    @Test
    void updateUser_Success() {
        User updatedData = new User("João Santos", "joao.santos@email.com", "+5511999999999",
                                   LocalDate.of(1990, 5, 15), UserType.VIEWER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, updatedData);

        assertNotNull(result);
        assertEquals("João Santos", user.getFullName());
        assertEquals("joao.santos@email.com", user.getEmail());
        assertEquals(UserType.VIEWER, user.getUserType());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_UserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User updatedData = new User("Nome", "email@test.com", "+5511999999999",
                                   LocalDate.of(1990, 5, 15), UserType.ADMIN);

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(999L, updatedData);
        });
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(999L);
        });

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void updateUser_EmailAlreadyExistsForDifferentUser() {
        User otherUser = new User("Pedro Silva", "pedro@email.com", "+5511777777777",
                                 LocalDate.of(1988, 2, 10), UserType.EDITOR);
        otherUser.setId(3L);

        User updatedData = new User("João Santos", "pedro@email.com", "+5511999999999",
                                   LocalDate.of(1990, 5, 15), UserType.VIEWER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, otherUser));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.updateUser(1L, updatedData);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_SameEmailForSameUser() {
        User updatedData = new User("João Santos", "joao@email.com", "+5511888888888",
                                   LocalDate.of(1990, 5, 15), UserType.VIEWER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, updatedData);

        assertNotNull(result);
        assertEquals("+5511888888888", user.getPhone());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_PhoneAlreadyExists() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(existingUser));

        User newUser = new User("Outro Nome", "novo@email.com", "+5511888888888",
                               LocalDate.of(1995, 3, 10), UserType.VIEWER);

        assertThrows(PhoneAlreadyExistsException.class, () -> {
            userService.createUser(newUser);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_PhoneAlreadyExistsForDifferentUser() {
        User otherUser = new User("Pedro Silva", "pedro@email.com", "+5511777777777",
                                 LocalDate.of(1988, 2, 10), UserType.EDITOR);
        otherUser.setId(3L);

        User updatedData = new User("João Santos", "joao.santos@email.com", "+5511777777777",
                                   LocalDate.of(1990, 5, 15), UserType.VIEWER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, otherUser));

        assertThrows(PhoneAlreadyExistsException.class, () -> {
            userService.updateUser(1L, updatedData);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_SamePhoneForSameUser() {
        User updatedData = new User("João Santos", "joao.santos@email.com", "+5511999999999",
                                   LocalDate.of(1990, 5, 15), UserType.VIEWER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, updatedData);

        assertNotNull(result);
        assertEquals("joao.santos@email.com", user.getEmail());
        verify(userRepository).save(user);
    }
}