package com.techmanage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techmanage.entity.User;
import com.techmanage.entity.UserType;
import com.techmanage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Comprehensive API Test based on documented input/output pairs
 * Tests all 18 scenarios from TEST_SPECIFICATION.md
 */
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.MethodName.class)
class ComprehensiveUserApiTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper;
    private User existingUser1;
    private User existingUser2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Clean database
        userRepository.deleteAll();

        // Create test data
        existingUser1 = new User("João Silva", "joao@email.com", "+55 11 99999-9999",
                LocalDate.of(1990, 5, 15), UserType.ADMIN);

        existingUser2 = new User("Maria Santos", "maria@email.com", "+55 11 88888-8888",
                LocalDate.of(1985, 3, 20), UserType.EDITOR);
    }

    // SUCCESS SCENARIOS

    @Test
    void test01_createUser_validData() throws Exception {
        User newUser = new User("Maria Silva", "maria@email.com", "+55 11 88888-8888",
                LocalDate.of(1985, 3, 20), UserType.EDITOR);

        String userJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fullName", is("Maria Silva")))
                .andExpect(jsonPath("$.email", is("maria@email.com")))
                .andExpect(jsonPath("$.phone", is("+55 11 88888-8888")))
                .andExpect(jsonPath("$.birthDate", is("1985-03-20")))
                .andExpect(jsonPath("$.userType", is("EDITOR")));
    }

    @Test
    void test02_getAllUsers_emptyList() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void test03_getAllUsers_withData() throws Exception {
        User savedUser = userRepository.save(existingUser1);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(savedUser.getId().intValue())))
                .andExpect(jsonPath("$[0].fullName", is("João Silva")))
                .andExpect(jsonPath("$[0].email", is("joao@email.com")))
                .andExpect(jsonPath("$[0].phone", is("+55 11 99999-9999")))
                .andExpect(jsonPath("$[0].birthDate", is("1990-05-15")))
                .andExpect(jsonPath("$[0].userType", is("ADMIN")));
    }

    @Test
    void test04_getUserById_found() throws Exception {
        User savedUser = userRepository.save(existingUser1);

        mockMvc.perform(get("/api/users/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedUser.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is("João Silva")))
                .andExpect(jsonPath("$.email", is("joao@email.com")))
                .andExpect(jsonPath("$.phone", is("+55 11 99999-9999")))
                .andExpect(jsonPath("$.birthDate", is("1990-05-15")))
                .andExpect(jsonPath("$.userType", is("ADMIN")));
    }

    @Test
    void test05_updateUser_validData() throws Exception {
        User savedUser = userRepository.save(existingUser1);

        User updateData = new User("João Santos Updated", "joao.updated@email.com", "+55 11 77777-7777",
                LocalDate.of(1990, 5, 15), UserType.VIEWER);

        String userJson = objectMapper.writeValueAsString(updateData);

        mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedUser.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is("João Santos Updated")))
                .andExpect(jsonPath("$.email", is("joao.updated@email.com")))
                .andExpect(jsonPath("$.phone", is("+55 11 77777-7777")))
                .andExpect(jsonPath("$.birthDate", is("1990-05-15")))
                .andExpect(jsonPath("$.userType", is("VIEWER")));
    }

    @Test
    void test06_deleteUser_success() throws Exception {
        User savedUser = userRepository.save(existingUser1);

        mockMvc.perform(delete("/api/users/" + savedUser.getId()))
                .andExpect(status().isNoContent());

        // Verify user is deleted
        mockMvc.perform(get("/api/users/" + savedUser.getId()))
                .andExpect(status().isNotFound());
    }

    // ERROR SCENARIOS - VALIDATION

    @Test
    void test07_createUser_multipleInvalidFields() throws Exception {
        String invalidUserJson = "{\n" +
                "    \"fullName\": \"\",\n" +
                "    \"email\": \"email-invalido\",\n" +
                "    \"phone\": \"telefone-invalido\",\n" +
                "    \"birthDate\": \"2030-01-01\",\n" +
                "    \"userType\": null\n" +
                "}";

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.fullName", is("Nome completo é obrigatório")))
                .andExpect(jsonPath("$.fieldErrors.email", is("Email deve ter um formato válido")))
                .andExpect(jsonPath("$.fieldErrors.phone", is("Telefone deve estar no formato internacional (ex: +55 11 99999-9999)")))
                .andExpect(jsonPath("$.fieldErrors.birthDate", is("Data de nascimento deve estar no passado")))
                .andExpect(jsonPath("$.fieldErrors.userType", is("Tipo de usuário é obrigatório")));
    }

    @Test
    void test08_createUser_duplicateEmail() throws Exception {
        userRepository.save(existingUser2); // Save Maria with maria@email.com

        User duplicateEmailUser = new User("Outro Usuario", "maria@email.com", "+55 11 99999-9999",
                LocalDate.of(1990, 1, 1), UserType.ADMIN);

        String userJson = objectMapper.writeValueAsString(duplicateEmailUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.email", is("Email já está em uso")));
    }

    @Test
    void test09_createUser_duplicatePhone() throws Exception {
        userRepository.save(existingUser2); // Save Maria with +55 11 88888-8888

        User duplicatePhoneUser = new User("Novo Usuario", "novo@email.com", "+55 11 88888-8888",
                LocalDate.of(1992, 6, 15), UserType.VIEWER);

        String userJson = objectMapper.writeValueAsString(duplicatePhoneUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.phone", is("Telefone já está em uso")));
    }

    @Test
    void test10_createUser_invalidUserType() throws Exception {
        String invalidUserJson = "{\n" +
                "    \"fullName\": \"Test User\",\n" +
                "    \"email\": \"test@email.com\",\n" +
                "    \"phone\": \"+55 11 66666-6666\",\n" +
                "    \"birthDate\": \"1995-12-25\",\n" +
                "    \"userType\": \"INVALID_TYPE\"\n" +
                "}";

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.userType", is("Tipo de usuário inválido. Valores aceitos: ADMIN, EDITOR, VIEWER")));
    }

    // ERROR SCENARIOS - NOT FOUND

    @Test
    void test11_getUserById_notFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Recurso não encontrado")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users/999")))
                .andExpect(jsonPath("$.fieldErrors.id", is("Usuário não encontrado com ID: 999")));
    }

    @Test
    void test12_updateUser_notFound() throws Exception {
        User updateData = new User("Test Update", "update@email.com", "+55 11 55555-5555",
                LocalDate.of(1988, 7, 10), UserType.ADMIN);

        String userJson = objectMapper.writeValueAsString(updateData);

        mockMvc.perform(put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Recurso não encontrado")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users/999")))
                .andExpect(jsonPath("$.fieldErrors.id", is("Usuário não encontrado com ID: 999")));
    }

    @Test
    void test13_updateUser_invalidData() throws Exception {
        User savedUser = userRepository.save(existingUser1);

        String invalidUserJson = "{\n" +
                "    \"fullName\": \"\",\n" +
                "    \"email\": \"email-invalido\",\n" +
                "    \"phone\": \"telefone-invalido\",\n" +
                "    \"birthDate\": \"2030-01-01\",\n" +
                "    \"userType\": null\n" +
                "}";

        mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.fullName", is("Nome completo é obrigatório")))
                .andExpect(jsonPath("$.fieldErrors.email", is("Email deve ter um formato válido")))
                .andExpect(jsonPath("$.fieldErrors.phone", is("Telefone deve estar no formato internacional (ex: +55 11 99999-9999)")))
                .andExpect(jsonPath("$.fieldErrors.birthDate", is("Data de nascimento deve estar no passado")))
                .andExpect(jsonPath("$.fieldErrors.userType", is("Tipo de usuário é obrigatório")));
    }

    @Test
    void test14_updateUser_duplicateEmail() throws Exception {
        User savedUser1 = userRepository.save(existingUser1);
        userRepository.save(existingUser2); // Maria with maria@email.com

        User updateData = new User("João Santos", "maria@email.com", "+55 11 77777-7777",
                LocalDate.of(1990, 5, 15), UserType.VIEWER);

        String userJson = objectMapper.writeValueAsString(updateData);

        mockMvc.perform(put("/api/users/" + savedUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.email", is("Email já está em uso")));
    }

    @Test
    void test15_updateUser_duplicatePhone() throws Exception {
        User savedUser1 = userRepository.save(existingUser1);
        userRepository.save(existingUser2); // Maria with +55 11 88888-8888

        User updateData = new User("João Santos", "joao.santos@email.com", "+55 11 88888-8888",
                LocalDate.of(1990, 5, 15), UserType.VIEWER);

        String userJson = objectMapper.writeValueAsString(updateData);

        mockMvc.perform(put("/api/users/" + savedUser1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Erro de validação")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users")))
                .andExpect(jsonPath("$.fieldErrors.phone", is("Telefone já está em uso")));
    }

    @Test
    void test16_deleteUser_notFound() throws Exception {
        mockMvc.perform(delete("/api/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Recurso não encontrado")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", is("/api/users/999")))
                .andExpect(jsonPath("$.fieldErrors.id", is("Usuário não encontrado com ID: 999")));
    }

    // EDGE CASES

    @Test
    void test17_createUser_validPhoneFormatVariations() throws Exception {
        User phoneVariationUser = new User("Test Phone", "phone@test.com", "+1 12 12345-6789",
                LocalDate.of(1990, 1, 1), UserType.ADMIN);

        String userJson = objectMapper.writeValueAsString(phoneVariationUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.fullName", is("Test Phone")))
                .andExpect(jsonPath("$.email", is("phone@test.com")))
                .andExpect(jsonPath("$.phone", is("+1 12 12345-6789")))
                .andExpect(jsonPath("$.birthDate", is("1990-01-01")))
                .andExpect(jsonPath("$.userType", is("ADMIN")));
    }

    @Test
    void test18_updateUser_sameEmailPhone_shouldAllow() throws Exception {
        User savedUser = userRepository.save(existingUser1);

        // Update with same email and phone - should be allowed
        User updateData = new User("João Silva Updated", "joao@email.com", "+55 11 99999-9999",
                LocalDate.of(1990, 5, 15), UserType.EDITOR);

        String userJson = objectMapper.writeValueAsString(updateData);

        mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedUser.getId().intValue())))
                .andExpect(jsonPath("$.fullName", is("João Silva Updated")))
                .andExpect(jsonPath("$.email", is("joao@email.com")))
                .andExpect(jsonPath("$.phone", is("+55 11 99999-9999")))
                .andExpect(jsonPath("$.birthDate", is("1990-05-15")))
                .andExpect(jsonPath("$.userType", is("EDITOR")));
    }
}