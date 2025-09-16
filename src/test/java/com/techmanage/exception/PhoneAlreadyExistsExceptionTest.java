package com.techmanage.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PhoneAlreadyExistsException class
 */
class PhoneAlreadyExistsExceptionTest {

    @Test
    void constructor_shouldSetMessage() {
        // Given
        String message = "Phone number is already in use";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleNullMessage() {
        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    void constructor_shouldHandleEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
    }

    @Test
    void exception_shouldBeInstanceOfRuntimeException() {
        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException("Test message");

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void exception_shouldBeThrowable() {
        // Given
        String message = "Phone already exists";

        // When & Then
        assertThrows(PhoneAlreadyExistsException.class, () -> {
            throw new PhoneAlreadyExistsException(message);
        });
    }

    @Test
    void exception_shouldPreserveStackTrace() {
        // Given
        String message = "Phone already exists";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(message);

        // Then
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
    }

    @Test
    void constructor_shouldHandlePhoneInMessage() {
        // Given
        String messageWithPhone = "Phone +55 11 99999-9999 is already in use";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(messageWithPhone);

        // Then
        assertEquals(messageWithPhone, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleSpecialCharacters() {
        // Given
        String specialMessage = "Telefone já está em uso: @#$%^&*()";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(specialMessage);

        // Then
        assertEquals(specialMessage, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleLongMessage() {
        // Given
        String longMessage = "Phone already exists: " + "+55 11 ".repeat(100) + "99999-9999";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(longMessage);

        // Then
        assertEquals(longMessage, exception.getMessage());
    }

    @Test
    void constructor_shouldHandleInternationalPhoneFormats() {
        // Given
        String messageWithIntlPhone = "Phone +1 555 123-4567 is already in use";

        // When
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(messageWithIntlPhone);

        // Then
        assertEquals(messageWithIntlPhone, exception.getMessage());
    }
}