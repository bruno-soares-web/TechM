package com.techmanage.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GlobalExceptionHandler class
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleUserNotFoundException_shouldReturnNotFound() {
        // Given
        String message = "User not found with ID: 123";
        UserNotFoundException exception = new UserNotFoundException(message);
        HttpServletRequest request = new MockHttpServletRequest("GET", "/api/user/123");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleUserNotFoundException(exception, request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(404, body.get("status"));
        assertEquals("Recurso não encontrado", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertEquals("/api/user/123", body.get("path"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertNotNull(fieldErrors);
        assertEquals("Usuário não encontrado com ID: 123", fieldErrors.get("id"));
    }

    @Test
    void handleUserNotFoundException_shouldExtractIdFromMessage() {
        // Given
        String message = "User not found with ID: 999";
        UserNotFoundException exception = new UserNotFoundException(message);
        HttpServletRequest request = new MockHttpServletRequest("GET", "/api/user/999");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleUserNotFoundException(exception, request);

        // Then
        Map<String, Object> body = response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertEquals("Usuário não encontrado com ID: 999", fieldErrors.get("id"));
        assertEquals("/api/user/999", body.get("path"));
    }

    @Test
    void handleUserNotFoundException_shouldHandleMessageWithoutId() {
        // Given
        String message = "User not found";
        UserNotFoundException exception = new UserNotFoundException(message);
        HttpServletRequest request = new MockHttpServletRequest("GET", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleUserNotFoundException(exception, request);

        // Then
        Map<String, Object> body = response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertEquals("Usuário não encontrado com ID: unknown", fieldErrors.get("id"));
        assertEquals("/api/user", body.get("path"));
    }

    @Test
    void handleEmailAlreadyExistsException_shouldReturnBadRequest() {
        // Given
        String message = "Email is already in use";
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleEmailAlreadyExistsException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Erro de validação", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertEquals("/api/user", body.get("path"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertNotNull(fieldErrors);
        assertEquals("Email já está em uso", fieldErrors.get("email"));
    }

    @Test
    void handlePhoneAlreadyExistsException_shouldReturnBadRequest() {
        // Given
        String message = "Phone number is already in use";
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(message);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handlePhoneAlreadyExistsException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Erro de validação", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertEquals("/api/user", body.get("path"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertNotNull(fieldErrors);
        assertEquals("Telefone já está em uso", fieldErrors.get("phone"));
    }

    @Test
    void handleValidationException_shouldReturnBadRequest() {
        // Given
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "user");
        bindingResult.addError(new FieldError("user", "fullName", "Nome completo é obrigatório"));
        bindingResult.addError(new FieldError("user", "email", "Email deve ter um formato válido"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleValidationException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Erro de validação", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertEquals("/api/user", body.get("path"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertNotNull(fieldErrors);
        assertEquals("Nome completo é obrigatório", fieldErrors.get("fullName"));
        assertEquals("Email deve ter um formato válido", fieldErrors.get("email"));
    }

    @Test
    void handleInvalidEnumValue_withUserType_shouldReturnBadRequest() {
        // Given - Simulate HttpMessageNotReadableException with UserType
        String message = "Cannot deserialize value of type `UserType` from String \"INVALID_TYPE\"";
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(message);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleInvalidEnumValue(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Erro de validação", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertEquals("/api/user", body.get("path"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertNotNull(fieldErrors);
        assertEquals("Tipo de usuário inválido. Valores aceitos: ADMIN, EDITOR, VIEWER", fieldErrors.get("userType"));
    }

    @Test
    void handleInvalidEnumValue_withoutUserType_shouldReturnBadRequest() {
        // Given - Simulate HttpMessageNotReadableException without UserType
        String message = "JSON parse error: Cannot deserialize";
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(message);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleInvalidEnumValue(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> body = response.getBody();
        assertEquals(400, body.get("status"));
        assertEquals("Erro de validação", body.get("error"));
        assertNotNull(body.get("timestamp"));
        assertEquals("/api/user", body.get("path"));

        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertNotNull(fieldErrors);
        assertEquals("Formato JSON inválido", fieldErrors.get("request"));
    }

    @Test
    void handleGenericException_shouldReturnInternalServerError() {
        // Given
        Exception exception = new Exception("Unexpected error");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());

        ErrorResponse body = response.getBody();
        assertEquals(500, body.getStatus());
        assertEquals("Internal server error", body.getMessage());
        assertNotNull(body.getTimestamp());
    }

    // Edge cases and null safety tests

    @Test
    void handleUserNotFoundException_shouldHandleNullMessage() {
        // Given
        UserNotFoundException exception = new UserNotFoundException(null);
        HttpServletRequest request = new MockHttpServletRequest("GET", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleUserNotFoundException(exception, request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertEquals("Usuário não encontrado com ID: unknown", fieldErrors.get("id"));
        assertEquals("/api/user", body.get("path"));
    }

    @Test
    void handleEmailAlreadyExistsException_shouldHandleNullMessage() {
        // Given
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(null);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleEmailAlreadyExistsException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertEquals("Email já está em uso", fieldErrors.get("email"));
        assertEquals("/api/user", body.get("path"));
    }

    @Test
    void handlePhoneAlreadyExistsException_shouldHandleNullMessage() {
        // Given
        PhoneAlreadyExistsException exception = new PhoneAlreadyExistsException(null);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handlePhoneAlreadyExistsException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertEquals("Telefone já está em uso", fieldErrors.get("phone"));
        assertEquals("/api/user", body.get("path"));
    }

    @Test
    void handleValidationException_shouldHandleEmptyBindingResult() {
        // Given
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "user");
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        HttpServletRequest request = new MockHttpServletRequest("POST", "/api/user");

        // When
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleValidationException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        @SuppressWarnings("unchecked")
        Map<String, String> fieldErrors = (Map<String, String>) body.get("fieldErrors");
        assertTrue(fieldErrors.isEmpty());
        assertEquals("/api/user", body.get("path"));
    }
}