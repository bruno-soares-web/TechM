package com.techmanage.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ErrorResponse class to improve test coverage
 */
class ErrorResponseTest {

    private ErrorResponse errorResponse;
    private String testMessage;
    private int testStatus;

    @BeforeEach
    void setUp() {
        testMessage = "Test error message";
        testStatus = 400;
        errorResponse = new ErrorResponse(testMessage, testStatus);
    }

    @Test
    void constructor_shouldSetMessageAndStatus() {
        // Given
        String message = "Error occurred";
        int status = 500;

        // When
        ErrorResponse response = new ErrorResponse(message, status);

        // Then
        assertEquals(message, response.getMessage());
        assertEquals(status, response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void constructor_shouldSetTimestampToCurrentTime() {
        // Given
        LocalDateTime before = LocalDateTime.now();

        // When
        ErrorResponse response = new ErrorResponse("Test", 400);
        LocalDateTime after = LocalDateTime.now();

        // Then
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isAfter(before) || response.getTimestamp().isEqual(before));
        assertTrue(response.getTimestamp().isBefore(after) || response.getTimestamp().isEqual(after));
    }

    @Test
    void getMessage_shouldReturnCorrectMessage() {
        // When
        String result = errorResponse.getMessage();

        // Then
        assertEquals(testMessage, result);
    }

    @Test
    void setMessage_shouldUpdateMessage() {
        // Given
        String newMessage = "Updated error message";

        // When
        errorResponse.setMessage(newMessage);

        // Then
        assertEquals(newMessage, errorResponse.getMessage());
    }

    @Test
    void setMessage_shouldHandleNullValue() {
        // When
        errorResponse.setMessage(null);

        // Then
        assertNull(errorResponse.getMessage());
    }

    @Test
    void setMessage_shouldHandleEmptyString() {
        // Given
        String emptyMessage = "";

        // When
        errorResponse.setMessage(emptyMessage);

        // Then
        assertEquals(emptyMessage, errorResponse.getMessage());
    }

    @Test
    void getStatus_shouldReturnCorrectStatus() {
        // When
        int result = errorResponse.getStatus();

        // Then
        assertEquals(testStatus, result);
    }

    @Test
    void setStatus_shouldUpdateStatus() {
        // Given
        int newStatus = 404;

        // When
        errorResponse.setStatus(newStatus);

        // Then
        assertEquals(newStatus, errorResponse.getStatus());
    }

    @Test
    void setStatus_shouldHandleNegativeValues() {
        // Given
        int negativeStatus = -1;

        // When
        errorResponse.setStatus(negativeStatus);

        // Then
        assertEquals(negativeStatus, errorResponse.getStatus());
    }

    @Test
    void setStatus_shouldHandleZeroValue() {
        // Given
        int zeroStatus = 0;

        // When
        errorResponse.setStatus(zeroStatus);

        // Then
        assertEquals(zeroStatus, errorResponse.getStatus());
    }

    @Test
    void setStatus_shouldHandleLargeValues() {
        // Given
        int largeStatus = 999;

        // When
        errorResponse.setStatus(largeStatus);

        // Then
        assertEquals(largeStatus, errorResponse.getStatus());
    }

    @Test
    void getTimestamp_shouldReturnCorrectTimestamp() {
        // When
        LocalDateTime result = errorResponse.getTimestamp();

        // Then
        assertNotNull(result);
        assertTrue(result.isBefore(LocalDateTime.now()) || result.isEqual(LocalDateTime.now()));
    }

    @Test
    void setTimestamp_shouldUpdateTimestamp() {
        // Given
        LocalDateTime newTimestamp = LocalDateTime.of(2023, 1, 1, 12, 0, 0);

        // When
        errorResponse.setTimestamp(newTimestamp);

        // Then
        assertEquals(newTimestamp, errorResponse.getTimestamp());
    }

    @Test
    void setTimestamp_shouldHandleNullValue() {
        // When
        errorResponse.setTimestamp(null);

        // Then
        assertNull(errorResponse.getTimestamp());
    }

    @Test
    void setTimestamp_shouldHandleFutureDate() {
        // Given
        LocalDateTime futureTimestamp = LocalDateTime.now().plusDays(1);

        // When
        errorResponse.setTimestamp(futureTimestamp);

        // Then
        assertEquals(futureTimestamp, errorResponse.getTimestamp());
    }

    @Test
    void setTimestamp_shouldHandlePastDate() {
        // Given
        LocalDateTime pastTimestamp = LocalDateTime.now().minusDays(1);

        // When
        errorResponse.setTimestamp(pastTimestamp);

        // Then
        assertEquals(pastTimestamp, errorResponse.getTimestamp());
    }

    // Integration tests for complete object state

    @Test
    void completeObjectState_afterMultipleOperations() {
        // Given
        String finalMessage = "Final error message";
        int finalStatus = 500;
        LocalDateTime finalTimestamp = LocalDateTime.of(2023, 12, 31, 23, 59, 59);

        // When
        errorResponse.setMessage(finalMessage);
        errorResponse.setStatus(finalStatus);
        errorResponse.setTimestamp(finalTimestamp);

        // Then
        assertEquals(finalMessage, errorResponse.getMessage());
        assertEquals(finalStatus, errorResponse.getStatus());
        assertEquals(finalTimestamp, errorResponse.getTimestamp());
    }

    @Test
    void objectConsistency_afterPartialUpdates() {
        // Given
        LocalDateTime originalTimestamp = errorResponse.getTimestamp();

        // When - Update only message
        errorResponse.setMessage("New message");

        // Then - Other fields remain unchanged
        assertEquals("New message", errorResponse.getMessage());
        assertEquals(testStatus, errorResponse.getStatus());
        assertEquals(originalTimestamp, errorResponse.getTimestamp());
    }

    // Edge cases and boundary tests

    @Test
    void constructor_withSpecialCharacters() {
        // Given
        String specialMessage = "Error: @#$%^&*()_+-=[]{}|;':\",./<>?";
        int status = 400;

        // When
        ErrorResponse response = new ErrorResponse(specialMessage, status);

        // Then
        assertEquals(specialMessage, response.getMessage());
        assertEquals(status, response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void constructor_withLongMessage() {
        // Given
        String longMessage = "A".repeat(1000); // 1000 character string
        int status = 500;

        // When
        ErrorResponse response = new ErrorResponse(longMessage, status);

        // Then
        assertEquals(longMessage, response.getMessage());
        assertEquals(status, response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void constructor_withNullMessage() {
        // When
        ErrorResponse response = new ErrorResponse(null, 400);

        // Then
        assertNull(response.getMessage());
        assertEquals(400, response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void allSettersAndGetters_workCorrectly() {
        // Given
        String message1 = "Message 1";
        String message2 = "Message 2";
        int status1 = 400;
        int status2 = 500;
        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.now().plusHours(1);

        // When & Then - Test multiple set/get cycles
        errorResponse.setMessage(message1);
        assertEquals(message1, errorResponse.getMessage());

        errorResponse.setMessage(message2);
        assertEquals(message2, errorResponse.getMessage());

        errorResponse.setStatus(status1);
        assertEquals(status1, errorResponse.getStatus());

        errorResponse.setStatus(status2);
        assertEquals(status2, errorResponse.getStatus());

        errorResponse.setTimestamp(time1);
        assertEquals(time1, errorResponse.getTimestamp());

        errorResponse.setTimestamp(time2);
        assertEquals(time2, errorResponse.getTimestamp());
    }
}