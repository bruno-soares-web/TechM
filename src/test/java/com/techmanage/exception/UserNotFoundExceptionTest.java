package com.techmanage.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserNotFoundException class
 */
class UserNotFoundExceptionTest {

    @Test
    void constructor_shouldSetMessage() {
        // Given
        String message = "User not found with ID: 123";

        // When
        UserNotFoundException exception = new UserNotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleNullMessage() {
        // When
        UserNotFoundException exception = new UserNotFoundException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    void constructor_shouldHandleEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        UserNotFoundException exception = new UserNotFoundException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    void exception_shouldBeInstanceOfRuntimeException() {
        // When
        UserNotFoundException exception = new UserNotFoundException("Test message");

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void exception_shouldBeThrowable() {
        // Given
        String message = "User not found";

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(message);
        });
    }

    @Test
    void exception_shouldPreserveStackTrace() {
        // Given
        String message = "User not found";

        // When
        UserNotFoundException exception = new UserNotFoundException(message);

        // Then
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
    }

    @Test
    void constructor_shouldHandleSpecialCharacters() {
        // Given
        String specialMessage = "User não encontrado: @#$%^&*()";

        // When
        UserNotFoundException exception = new UserNotFoundException(specialMessage);

        // Then
        assertEquals(specialMessage, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleLongMessage() {
        // Given
        String longMessage = "User not found: " + new String(new char[1000]).replace('\0', 'A');

        // When
        UserNotFoundException exception = new UserNotFoundException(longMessage);

        // Then
        assertEquals(longMessage, exception.getMessage());
    }
}