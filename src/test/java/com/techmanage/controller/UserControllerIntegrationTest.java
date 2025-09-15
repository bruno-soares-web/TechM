package com.techmanage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureTestMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestMvc
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        userRepository.deleteAll();

        testUser = new User("João Silva", "joao@email.com", "+5511999999999",
                           LocalDate.of(1990, 5, 15), UserType.ADMIN);
    }

    @Test
    void createUser_Success() throws Exception {
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao@email.com")))
                .andExpect(jsonPath("$.userType", is("ADMIN")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void createUser_InvalidData() throws Exception {
        User invalidUser = new User("", "email-invalido", "telefone-invalido",
                                   LocalDate.now().plusDays(1), null);

        String userJson = objectMapper.writeValueAsString(invalidUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void getAllUsers_Success() throws Exception {
        User savedUser = userRepository.save(testUser);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fullName", is("João Silva")))
                .andExpect(jsonPath("$[0].email", is("joao@email.com")));
    }

    @Test
    void getUserById_Success() throws Exception {
        User savedUser = userRepository.save(testUser);

        mockMvc.perform(get("/api/users/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao@email.com")))
                .andExpect(jsonPath("$.id", is(savedUser.getId().intValue())));
    }

    @Test
    void getUserById_NotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", containsString("Usuário não encontrado")));
    }

    @Test
    void updateUser_Success() throws Exception {
        User savedUser = userRepository.save(testUser);

        User updatedUser = new User("João Santos", "joao.santos@email.com", "+5511888888888",
                                   LocalDate.of(1990, 5, 15), UserType.EDITOR);

        String userJson = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("João Santos")))
                .andExpect(jsonPath("$.email", is("joao.santos@email.com")))
                .andExpect(jsonPath("$.userType", is("EDITOR")));
    }

    @Test
    void updateUser_NotFound() throws Exception {
        String userJson = objectMapper.writeValueAsString(testUser);

        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    void deleteUser_Success() throws Exception {
        User savedUser = userRepository.save(testUser);

        mockMvc.perform(delete("/api/users/" + savedUser.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/" + savedUser.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_NotFound() throws Exception {
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));
    }
}