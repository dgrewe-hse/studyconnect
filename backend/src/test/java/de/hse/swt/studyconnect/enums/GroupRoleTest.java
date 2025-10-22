/*
 * Copyright (c) 2025 StudyConnect Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.hse.swt.studyconnect.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GroupRole enum.
 * 
 * Tests the GroupRole enum using equivalence class partitioning and boundary value analysis.
 * Covers all enum values, ordinal positions, and string representations.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("GroupRole Enum Tests")
class GroupRoleTest {

    /**
     * Test that all expected enum values exist.
     * 
     * Equivalence Class: Valid enum values
     * Boundary Value: Both role types
     */
    @Test
    @DisplayName("Should contain all expected role types")
    void shouldContainAllExpectedRoleTypes() {
        GroupRole[] roles = GroupRole.values();
        
        assertEquals(2, roles.length, "Should have exactly 2 role types");
        assertArrayEquals(new GroupRole[]{GroupRole.STUDENT, GroupRole.ADMIN}, 
                         roles, "Should contain STUDENT and ADMIN roles");
    }

    /**
     * Test enum value retrieval by name.
     * 
     * Equivalence Class: Valid enum names
     * Boundary Value: Each enum value name
     */
    @ParameterizedTest
    @ValueSource(strings = {"STUDENT", "ADMIN"})
    @DisplayName("Should retrieve enum value by name")
    void shouldRetrieveEnumValueByName(String roleName) {
        GroupRole role = GroupRole.valueOf(roleName);
        assertNotNull(role, "Should retrieve enum value for name: " + roleName);
        assertEquals(roleName, role.name(), "Enum name should match input");
    }

    /**
     * Test enum ordinal positions.
     * 
     * Equivalence Class: Valid ordinal positions
     * Boundary Value: First (0) and last (1) positions
     */
    @Test
    @DisplayName("Should have correct ordinal positions")
    void shouldHaveCorrectOrdinalPositions() {
        assertEquals(0, GroupRole.STUDENT.ordinal(), "STUDENT should have ordinal 0");
        assertEquals(1, GroupRole.ADMIN.ordinal(), "ADMIN should have ordinal 1");
    }

    /**
     * Test enum string representation.
     * 
     * Equivalence Class: Valid enum values
     * Boundary Value: Each enum value
     */
    @ParameterizedTest
    @EnumSource(GroupRole.class)
    @DisplayName("Should have correct string representation")
    void shouldHaveCorrectStringRepresentation(GroupRole role) {
        assertNotNull(role.toString(), "toString() should not return null");
        assertEquals(role.name(), role.toString(), "toString() should return enum name");
    }

    /**
     * Test enum equality and identity.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value compared to itself
     */
    @ParameterizedTest
    @EnumSource(GroupRole.class)
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself(GroupRole role) {
        assertEquals(role, role, "Enum should be equal to itself");
        assertSame(role, role, "Enum should be identical to itself");
    }

    /**
     * Test enum inequality.
     * 
     * Equivalence Class: Different enum values
     * Boundary Value: Each enum value compared to the other
     */
    @Test
    @DisplayName("Should not be equal to different roles")
    void shouldNotBeEqualToDifferentRoles() {
        assertNotEquals(GroupRole.STUDENT, GroupRole.ADMIN, "STUDENT should not equal ADMIN");
        assertNotEquals(GroupRole.ADMIN, GroupRole.STUDENT, "ADMIN should not equal STUDENT");
    }

    /**
     * Test enum hash code consistency.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value
     */
    @ParameterizedTest
    @EnumSource(GroupRole.class)
    @DisplayName("Should have consistent hash code")
    void shouldHaveConsistentHashCode(GroupRole role) {
        int hashCode1 = role.hashCode();
        int hashCode2 = role.hashCode();
        
        assertEquals(hashCode1, hashCode2, "Hash code should be consistent");
        assertTrue(hashCode1 != 0, "Hash code should not be zero");
    }

    /**
     * Test enum comparison.
     * 
     * Equivalence Class: Different enum values
     * Boundary Value: Each enum value compared to the other
     */
    @Test
    @DisplayName("Should compare correctly based on ordinal")
    void shouldCompareCorrectlyBasedOnOrdinal() {
        assertTrue(GroupRole.STUDENT.compareTo(GroupRole.ADMIN) < 0, "STUDENT should be less than ADMIN");
        assertTrue(GroupRole.ADMIN.compareTo(GroupRole.STUDENT) > 0, "ADMIN should be greater than STUDENT");
    }

    /**
     * Test enum self-comparison.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value compared to itself
     */
    @ParameterizedTest
    @EnumSource(GroupRole.class)
    @DisplayName("Should compare equal to itself")
    void shouldCompareEqualToItself(GroupRole role) {
        assertEquals(0, role.compareTo(role), "Enum should compare equal to itself");
    }

    /**
     * Test invalid enum name handling.
     * 
     * Equivalence Class: Invalid enum names
     * Boundary Value: Non-existent enum names
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "INVALID", "student", "admin", "MEMBER", "MODERATOR", "OWNER"})
    @DisplayName("Should throw exception for invalid enum names")
    void shouldThrowExceptionForInvalidEnumNames(String invalidName) {
        assertThrows(IllegalArgumentException.class, 
                    () -> GroupRole.valueOf(invalidName),
                    "Should throw IllegalArgumentException for invalid name: " + invalidName);
    }

    /**
     * Test enum values array immutability.
     * 
     * Equivalence Class: Enum values array
     * Boundary Value: Array length and content
     */
    @Test
    @DisplayName("Should have immutable values array")
    void shouldHaveImmutableValuesArray() {
        GroupRole[] values1 = GroupRole.values();
        GroupRole[] values2 = GroupRole.values();
        
        assertNotSame(values1, values2, "values() should return new array each time");
        assertArrayEquals(values1, values2, "values() should return consistent content");
    }
}
