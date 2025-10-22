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
 * Unit tests for TaskPriority enum.
 * 
 * Tests the TaskPriority enum using equivalence class partitioning and boundary value analysis.
 * Covers all enum values, ordinal positions, and string representations.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("TaskPriority Enum Tests")
class TaskPriorityTest {

    /**
     * Test that all expected enum values exist.
     * 
     * Equivalence Class: Valid enum values
     * Boundary Value: All three priority levels
     */
    @Test
    @DisplayName("Should contain all expected priority levels")
    void shouldContainAllExpectedPriorityLevels() {
        TaskPriority[] priorities = TaskPriority.values();
        
        assertEquals(3, priorities.length, "Should have exactly 3 priority levels");
        assertArrayEquals(new TaskPriority[]{TaskPriority.LOW, TaskPriority.MEDIUM, TaskPriority.HIGH}, 
                         priorities, "Should contain LOW, MEDIUM, and HIGH priorities");
    }

    /**
     * Test enum value retrieval by name.
     * 
     * Equivalence Class: Valid enum names
     * Boundary Value: Each enum value name
     */
    @ParameterizedTest
    @ValueSource(strings = {"LOW", "MEDIUM", "HIGH"})
    @DisplayName("Should retrieve enum value by name")
    void shouldRetrieveEnumValueByName(String priorityName) {
        TaskPriority priority = TaskPriority.valueOf(priorityName);
        assertNotNull(priority, "Should retrieve enum value for name: " + priorityName);
        assertEquals(priorityName, priority.name(), "Enum name should match input");
    }

    /**
     * Test enum ordinal positions.
     * 
     * Equivalence Class: Valid ordinal positions
     * Boundary Value: First (0), middle (1), last (2) positions
     */
    @Test
    @DisplayName("Should have correct ordinal positions")
    void shouldHaveCorrectOrdinalPositions() {
        assertEquals(0, TaskPriority.LOW.ordinal(), "LOW should have ordinal 0");
        assertEquals(1, TaskPriority.MEDIUM.ordinal(), "MEDIUM should have ordinal 1");
        assertEquals(2, TaskPriority.HIGH.ordinal(), "HIGH should have ordinal 2");
    }

    /**
     * Test enum string representation.
     * 
     * Equivalence Class: Valid enum values
     * Boundary Value: Each enum value
     */
    @ParameterizedTest
    @EnumSource(TaskPriority.class)
    @DisplayName("Should have correct string representation")
    void shouldHaveCorrectStringRepresentation(TaskPriority priority) {
        assertNotNull(priority.toString(), "toString() should not return null");
        assertEquals(priority.name(), priority.toString(), "toString() should return enum name");
    }

    /**
     * Test enum equality and identity.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value compared to itself
     */
    @ParameterizedTest
    @EnumSource(TaskPriority.class)
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself(TaskPriority priority) {
        assertEquals(priority, priority, "Enum should be equal to itself");
        assertSame(priority, priority, "Enum should be identical to itself");
    }

    /**
     * Test enum inequality.
     * 
     * Equivalence Class: Different enum values
     * Boundary Value: Each enum value compared to others
     */
    @Test
    @DisplayName("Should not be equal to different priorities")
    void shouldNotBeEqualToDifferentPriorities() {
        assertNotEquals(TaskPriority.LOW, TaskPriority.MEDIUM, "LOW should not equal MEDIUM");
        assertNotEquals(TaskPriority.LOW, TaskPriority.HIGH, "LOW should not equal HIGH");
        assertNotEquals(TaskPriority.MEDIUM, TaskPriority.HIGH, "MEDIUM should not equal HIGH");
    }

    /**
     * Test enum hash code consistency.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value
     */
    @ParameterizedTest
    @EnumSource(TaskPriority.class)
    @DisplayName("Should have consistent hash code")
    void shouldHaveConsistentHashCode(TaskPriority priority) {
        int hashCode1 = priority.hashCode();
        int hashCode2 = priority.hashCode();
        
        assertEquals(hashCode1, hashCode2, "Hash code should be consistent");
        assertTrue(hashCode1 != 0, "Hash code should not be zero");
    }

    /**
     * Test enum comparison.
     * 
     * Equivalence Class: Different enum values
     * Boundary Value: Each enum value compared to others
     */
    @Test
    @DisplayName("Should compare correctly based on ordinal")
    void shouldCompareCorrectlyBasedOnOrdinal() {
        assertTrue(TaskPriority.LOW.compareTo(TaskPriority.MEDIUM) < 0, "LOW should be less than MEDIUM");
        assertTrue(TaskPriority.LOW.compareTo(TaskPriority.HIGH) < 0, "LOW should be less than HIGH");
        assertTrue(TaskPriority.MEDIUM.compareTo(TaskPriority.HIGH) < 0, "MEDIUM should be less than HIGH");
        assertTrue(TaskPriority.MEDIUM.compareTo(TaskPriority.LOW) > 0, "MEDIUM should be greater than LOW");
        assertTrue(TaskPriority.HIGH.compareTo(TaskPriority.LOW) > 0, "HIGH should be greater than LOW");
        assertTrue(TaskPriority.HIGH.compareTo(TaskPriority.MEDIUM) > 0, "HIGH should be greater than MEDIUM");
    }

    /**
     * Test enum self-comparison.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value compared to itself
     */
    @ParameterizedTest
    @EnumSource(TaskPriority.class)
    @DisplayName("Should compare equal to itself")
    void shouldCompareEqualToItself(TaskPriority priority) {
        assertEquals(0, priority.compareTo(priority), "Enum should compare equal to itself");
    }

    /**
     * Test invalid enum name handling.
     * 
     * Equivalence Class: Invalid enum names
     * Boundary Value: Non-existent enum names
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "INVALID", "low", "medium", "high", "NONE", "CRITICAL"})
    @DisplayName("Should throw exception for invalid enum names")
    void shouldThrowExceptionForInvalidEnumNames(String invalidName) {
        assertThrows(IllegalArgumentException.class, 
                    () -> TaskPriority.valueOf(invalidName),
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
        TaskPriority[] values1 = TaskPriority.values();
        TaskPriority[] values2 = TaskPriority.values();
        
        assertNotSame(values1, values2, "values() should return new array each time");
        assertArrayEquals(values1, values2, "values() should return consistent content");
    }
}
