package com.techmanage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        // Extract ID from message if possible
        String message = ex.getMessage();
        String id = "unknown";
        if (message.contains("ID:")) {
            id = message.substring(message.indexOf("ID:") + 4).trim();
        }

        fieldErrors.put("id", "Usuário não encontrado com ID: " + id);

        response.put("fieldErrors", fieldErrors);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Recurso não encontrado");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        fieldErrors.put("email", "Email já está em uso");

        response.put("fieldErrors", fieldErrors);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de validação");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        fieldErrors.put("phone", "Telefone já está em uso");

        response.put("fieldErrors", fieldErrors);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de validação");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        // Ordem específica dos campos
        String[] fieldOrder = {"fullName", "email", "phone", "birthDate", "userType"};

        // Coletar todos os erros primeiro
        Map<String, String> allErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            allErrors.put(error.getField(), error.getDefaultMessage())
        );

        // Adicionar erros na ordem especificada
        for (String field : fieldOrder) {
            if (allErrors.containsKey(field)) {
                fieldErrors.put(field, allErrors.get(field));
            }
        }

        response.put("fieldErrors", fieldErrors);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de validação");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidEnumValue(HttpMessageNotReadableException ex, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        if (ex.getMessage().contains("UserType")) {
            fieldErrors.put("userType", "Tipo de usuário inválido. Valores aceitos: ADMIN, EDITOR, VIEWER");
        } else {
            fieldErrors.put("request", "Formato JSON inválido");
        }

        response.put("fieldErrors", fieldErrors);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Erro de validação");
        response.put("path", request.getRequestURI());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}