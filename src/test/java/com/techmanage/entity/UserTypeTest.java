package com.techmanage.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {

    @Test
    void testEnumValues() {
        UserType[] expectedValues = {UserType.ADMIN, UserType.EDITOR, UserType.VIEWER};
        UserType[] actualValues = UserType.values();

        assertEquals(3, actualValues.length);
        assertArrayEquals(expectedValues, actualValues);
    }

    @Test
    void testEnumValueOf() {
        assertEquals(UserType.ADMIN, UserType.valueOf("ADMIN"));
        assertEquals(UserType.EDITOR, UserType.valueOf("EDITOR"));
        assertEquals(UserType.VIEWER, UserType.valueOf("VIEWER"));
    }

    @Test
    void testEnumValueOfInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserType.valueOf("INVALID");
        });
    }

    @Test
    void testEnumNames() {
        assertEquals("ADMIN", UserType.ADMIN.name());
        assertEquals("EDITOR", UserType.EDITOR.name());
        assertEquals("VIEWER", UserType.VIEWER.name());
    }

    @Test
    void testEnumOrdinals() {
        assertEquals(0, UserType.ADMIN.ordinal());
        assertEquals(1, UserType.EDITOR.ordinal());
        assertEquals(2, UserType.VIEWER.ordinal());
    }

    @Test
    void testEnumEquality() {
        assertEquals(UserType.ADMIN, UserType.ADMIN);
        assertEquals(UserType.EDITOR, UserType.EDITOR);
        assertEquals(UserType.VIEWER, UserType.VIEWER);

        assertNotEquals(UserType.ADMIN, UserType.EDITOR);
        assertNotEquals(UserType.EDITOR, UserType.VIEWER);
        assertNotEquals(UserType.VIEWER, UserType.ADMIN);
    }

    @Test
    void testEnumToString() {
        assertEquals("ADMIN", UserType.ADMIN.toString());
        assertEquals("EDITOR", UserType.EDITOR.toString());
        assertEquals("VIEWER", UserType.VIEWER.toString());
    }
}