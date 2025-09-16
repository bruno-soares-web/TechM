package com.techmanage.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testDefaultConstructor() {
        User newUser = new User();
        assertNull(newUser.getId());
        assertNull(newUser.getFullName());
        assertNull(newUser.getEmail());
        assertNull(newUser.getPhone());
        assertNull(newUser.getBirthDate());
        assertNull(newUser.getUserType());
    }

    @Test
    void testParameterizedConstructor() {
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        User newUser = new User("João Silva", "joao@email.com", "+5511999999999", birthDate, UserType.ADMIN);

        assertNull(newUser.getId());
        assertEquals("João Silva", newUser.getFullName());
        assertEquals("joao@email.com", newUser.getEmail());
        assertEquals("+55 11 99999-9999", newUser.getPhone()); // Formatted phone
        assertEquals(birthDate, newUser.getBirthDate());
        assertEquals(UserType.ADMIN, newUser.getUserType());
    }

    @Test
    void testSettersAndGetters() {
        LocalDate birthDate = LocalDate.of(1990, 5, 15);

        user.setId(1L);
        user.setFullName("João Silva");
        user.setEmail("joao@email.com");
        user.setPhone("+5511999999999");
        user.setBirthDate(birthDate);
        user.setUserType(UserType.ADMIN);

        assertEquals(1L, user.getId());
        assertEquals("João Silva", user.getFullName());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("+55 11 99999-9999", user.getPhone()); // Auto-formatted
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(UserType.ADMIN, user.getUserType());
    }

    @Test
    void testPhoneFormattingAlreadyFormatted() {
        user.setPhone("+55 11 99999-9999");
        assertEquals("+55 11 99999-9999", user.getPhone());
    }

    @Test
    void testPhoneFormattingWithoutFormatting() {
        user.setPhone("+5511999999999");
        assertEquals("+55 11 99999-9999", user.getPhone());
    }

    @Test
    void testPhoneFormattingWithLongerNumber() {
        user.setPhone("+551199999999");
        assertEquals("+55 11 9999-9999", user.getPhone());
    }

    @Test
    void testPhoneFormattingInvalidFormat() {
        user.setPhone("invalid-phone");
        assertEquals("invalid-phone", user.getPhone());
    }

    @Test
    void testPhoneFormattingNull() {
        user.setPhone(null);
        assertNull(user.getPhone());
    }

    @Test
    void testPhoneFormattingShortNumber() {
        user.setPhone("+55119999");
        assertEquals("+55119999", user.getPhone());
    }

    @Test
    void testToString() {
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        user.setId(1L);
        user.setFullName("João Silva");
        user.setEmail("joao@email.com");
        user.setPhone("+5511999999999");
        user.setBirthDate(birthDate);
        user.setUserType(UserType.ADMIN);

        String expected = "User{id=1, fullName='João Silva', email='joao@email.com', phone='+5511999999999', birthDate=1990-05-15, userType=ADMIN}";
        assertEquals(expected, user.toString());
    }

    @Test
    void testToStringWithNullValues() {
        String expected = "User{id=null, fullName='null', email='null', phone='null', birthDate=null, userType=null}";
        assertEquals(expected, user.toString());
    }

    @Test
    void testPhoneFormattingBrazilianPattern() {
        // Test with 11 digit Brazilian number
        user.setPhone("+5511987654321");
        assertEquals("+55 11 98765-4321", user.getPhone());
    }

    @Test
    void testPhoneFormattingBrazilianPattern10Digit() {
        // Test with 10 digit Brazilian number
        user.setPhone("+551187654321");
        assertEquals("+55 11 8765-4321", user.getPhone());
    }

    @Test
    void testPhoneFormattingEdgeCaseMinimumDigits() {
        // Test with exactly 10 digits after country code
        user.setPhone("+551234567890");
        assertEquals("+55 12 3456-7890", user.getPhone());
    }
}