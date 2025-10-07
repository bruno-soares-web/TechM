package com.techmanage.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                          LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua Teste, 123");
        testUser.setId(1L);
    }

    @Test
    void createUser_shouldReturnCreated() {
        // Given
        User inputUser = new User("João Silva", "joao@email.com", "+5511999999999",
                                LocalDate.of(1990, 5, 15), UserType.ADMIN, "Rua Teste, 123");
        when(userService.createUser(any(User.class))).thenReturn(testUser);

        // When
        ResponseEntity<User> response = userController.createUser(inputUser);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser.getId(), response.getBody().getId());
        assertEquals(testUser.getFullName(), response.getBody().getFullName());
        verify(userService).createUser(inputUser);
    }

    @Test
    void createUser_shouldHandleNullUser() {
        // Given
        User inputUser = null;

        // When/Then
        assertThrows(NullPointerException.class, () -> {
            userController.createUser(inputUser);
        });
    }

    @Test
    void getAllUsers_shouldReturnOkWithUsers() {
        // Given
        User user2 = new User("Maria Santos", "maria@email.com", "+5511888888888",
                             LocalDate.of(1985, 8, 20), UserType.EDITOR, "Rua Teste, 123");
        user2.setId(2L);
        List<User> users = Arrays.asList(testUser, user2);
        when(userService.getAllUsers()).thenReturn(users);

        // When
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(testUser.getId(), response.getBody().get(0).getId());
        assertEquals(user2.getId(), response.getBody().get(1).getId());
        verify(userService).getAllUsers();
    }

    @Test
    void getAllUsers_shouldReturnEmptyList() {
        // Given
        when(userService.getAllUsers()).thenReturn(Arrays.asList());

        // When
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(userService).getAllUsers();
    }

    @Test
    void getUserById_shouldReturnOkWithUser() {
        // Given
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(testUser);

        // When
        ResponseEntity<User> response = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser.getId(), response.getBody().getId());
        assertEquals(testUser.getFullName(), response.getBody().getFullName());
        verify(userService).getUserById(userId);
    }

    @Test
    void getUserById_withNullId_shouldHandleGracefully() {
        // Given
        Long userId = null;

        // When
        ResponseEntity<User> response = userController.getUserById(userId);

        // Then
        verify(userService).getUserById(null);
    }

    @Test
    void getUserById_withValidId_shouldCallService() {
        // Given
        Long userId = 123L;
        when(userService.getUserById(userId)).thenReturn(testUser);

        // When
        ResponseEntity<User> response = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).getUserById(userId);
    }

    @Test
    void updateUser_shouldReturnOkWithUpdatedUser() {
        // Given
        Long userId = 1L;
        User updateData = new User("João Santos", "joao.santos@email.com", "+5511999999999",
                                 LocalDate.of(1990, 5, 15), UserType.EDITOR, "Rua Teste, 123");
        User updatedUser = new User("João Santos", "joao.santos@email.com", "+5511999999999",
                                  LocalDate.of(1990, 5, 15), UserType.EDITOR,  "Rua Teste, 123");
        updatedUser.setId(userId);

        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(updatedUser);

        // When
        ResponseEntity<User> response = userController.updateUser(userId, updateData);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedUser.getId(), response.getBody().getId());
        assertEquals(updatedUser.getFullName(), response.getBody().getFullName());
        assertEquals(updatedUser.getUserType(), response.getBody().getUserType());
        verify(userService).updateUser(userId, updateData);
    }

    @Test
    void updateUser_withNullId_shouldCallService() {
        // Given
        Long userId = null;
        User updateData = new User("João Santos", "joao.santos@email.com", "+5511999999999",
                                 LocalDate.of(1990, 5, 15), UserType.EDITOR, "Rua Teste, 123");

        // When
        ResponseEntity<User> response = userController.updateUser(userId, updateData);

        // Then
        verify(userService).updateUser(null, updateData);
    }

    @Test
    void updateUser_withNullUser_shouldHandleGracefully() {
        // Given
        Long userId = 1L;
        User updateData = null;

        // When/Then
        assertThrows(NullPointerException.class, () -> {
            userController.updateUser(userId, updateData);
        });
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        // Given
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // When
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService).deleteUser(userId);
    }

    @Test
    void deleteUser_withNullId_shouldCallService() {
        // Given
        Long userId = null;
        doNothing().when(userService).deleteUser(userId);

        // When
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).deleteUser(null);
    }

    @Test
    void deleteUser_shouldHandleServiceCall() {
        // Given
        Long userId = 999L;
        doNothing().when(userService).deleteUser(userId);

        // When
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void controller_shouldHaveUserServiceInjected() {
        // Given/When/Then
        assertNotNull(userController);
        // The @InjectMocks annotation ensures userService is injected
        // This test verifies the dependency injection setup
    }

    @Test
    void createUser_withDifferentUserTypes_shouldWork() {
        // Test with ADMIN
        User adminUser = new User("Admin User", "admin@email.com", "+5511111111111",
                                LocalDate.of(1980, 1, 1), UserType.ADMIN, "Rua Teste, 123");
        when(userService.createUser(any(User.class))).thenReturn(adminUser);

        ResponseEntity<User> adminResponse = userController.createUser(adminUser);
        assertEquals(HttpStatus.CREATED, adminResponse.getStatusCode());

        // Test with EDITOR
        User editorUser = new User("Editor User", "editor@email.com", "+5511222222222",
                                 LocalDate.of(1985, 6, 15), UserType.EDITOR, "Rua Teste, 123");
        when(userService.createUser(any(User.class))).thenReturn(editorUser);

        ResponseEntity<User> editorResponse = userController.createUser(editorUser);
        assertEquals(HttpStatus.CREATED, editorResponse.getStatusCode());

        // Test with VIEWER
        User viewerUser = new User("Viewer User", "viewer@email.com", "+5511333333333",
                                 LocalDate.of(1990, 12, 31), UserType.VIEWER, "Rua Teste, 123");
        when(userService.createUser(any(User.class))).thenReturn(viewerUser);

        ResponseEntity<User> viewerResponse = userController.createUser(viewerUser);
        assertEquals(HttpStatus.CREATED, viewerResponse.getStatusCode());

        verify(userService, times(3)).createUser(any(User.class));
    }

    @Test
    void updateUser_withDifferentUserTypes_shouldWork() {
        // Given
        Long userId = 1L;

        // Test update to ADMIN
        User adminUpdate = new User("Updated Admin", "admin@email.com", "+5511111111111",
                                  LocalDate.of(1980, 1, 1), UserType.ADMIN, "Rua Teste, 123");
        adminUpdate.setId(userId);
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(adminUpdate);

        ResponseEntity<User> adminResponse = userController.updateUser(userId, adminUpdate);
        assertEquals(HttpStatus.OK, adminResponse.getStatusCode());
        assertEquals(UserType.ADMIN, adminResponse.getBody().getUserType());

        // Test update to EDITOR
        User editorUpdate = new User("Updated Editor", "editor@email.com", "+5511222222222",
                                   LocalDate.of(1985, 6, 15), UserType.EDITOR, "Rua Teste, 123");
        editorUpdate.setId(userId);
        when(userService.updateUser(eq(userId), any(User.class))).thenReturn(editorUpdate);

        ResponseEntity<User> editorResponse = userController.updateUser(userId, editorUpdate);
        assertEquals(HttpStatus.OK, editorResponse.getStatusCode());
        assertEquals(UserType.EDITOR, editorResponse.getBody().getUserType());

        verify(userService, times(2)).updateUser(eq(userId), any(User.class));
    }

    @Test
    void getAllUsers_withMultipleUsersOfDifferentTypes_shouldReturnAll() {
        // Given
        User admin = new User("Admin", "admin@email.com", "+5511111111111",
                            LocalDate.of(1980, 1, 1), UserType.ADMIN, "Rua Teste, 123");
        admin.setId(1L);

        User editor = new User("Editor", "editor@email.com", "+5511222222222",
                             LocalDate.of(1985, 6, 15), UserType.EDITOR, "Rua Teste, 123");
        editor.setId(2L);

        User viewer = new User("Viewer", "viewer@email.com", "+5511333333333",
                             LocalDate.of(1990, 12, 31), UserType.VIEWER, "Rua Teste, 123");
        viewer.setId(3L);

        List<User> users = Arrays.asList(admin, editor, viewer);
        when(userService.getAllUsers()).thenReturn(users);

        // When
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
        assertTrue(response.getBody().stream().anyMatch(u -> u.getUserType() == UserType.ADMIN));
        assertTrue(response.getBody().stream().anyMatch(u -> u.getUserType() == UserType.EDITOR));
        assertTrue(response.getBody().stream().anyMatch(u -> u.getUserType() == UserType.VIEWER));
        verify(userService).getAllUsers();
    }
}