package com.techmanage.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailAlreadyExistsException class
 */
class EmailAlreadyExistsExceptionTest {

    @Test
    void constructor_shouldSetMessage() {
        // Given
        String message = "Email is already in use";

        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleNullMessage() {
        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    void constructor_shouldHandleEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    void exception_shouldBeInstanceOfRuntimeException() {
        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("Test message");

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void exception_shouldBeThrowable() {
        // Given
        String message = "Email already exists";

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> {
            throw new EmailAlreadyExistsException(message);
        });
    }

    @Test
    void exception_shouldPreserveStackTrace() {
        // Given
        String message = "Email already exists";

        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message);

        // Then
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
    }

    @Test
    void constructor_shouldHandleEmailInMessage() {
        // Given
        String messageWithEmail = "Email user@example.com is already in use";

        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(messageWithEmail);

        // Then
        assertEquals(messageWithEmail, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleSpecialCharacters() {
        // Given
        String specialMessage = "Email já está em uso: @#$%^&*()";

        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(specialMessage);

        // Then
        assertEquals(specialMessage, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleLongMessage() {
        // Given
        String longMessage = "Email already exists: " + "test@".repeat(100) + "example.com";

        // When
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(longMessage);

        // Then
        assertEquals(longMessage, exception.getMessage());
    }
}