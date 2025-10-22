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
 * Unit tests for TaskStatus enum.
 * 
 * Tests the TaskStatus enum using equivalence class partitioning and boundary value analysis.
 * Covers all enum values, ordinal positions, and string representations.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("TaskStatus Enum Tests")
class TaskStatusTest {

    /**
     * Test that all expected enum values exist.
     * 
     * Equivalence Class: Valid enum values
     * Boundary Value: All three status levels
     */
    @Test
    @DisplayName("Should contain all expected status levels")
    void shouldContainAllExpectedStatusLevels() {
        TaskStatus[] statuses = TaskStatus.values();
        
        assertEquals(3, statuses.length, "Should have exactly 3 status levels");
        assertArrayEquals(new TaskStatus[]{TaskStatus.OPEN, TaskStatus.IN_PROGRESS, TaskStatus.COMPLETED}, 
                         statuses, "Should contain OPEN, IN_PROGRESS, and COMPLETED statuses");
    }

    /**
     * Test enum value retrieval by name.
     * 
     * Equivalence Class: Valid enum names
     * Boundary Value: Each enum value name
     */
    @ParameterizedTest
    @ValueSource(strings = {"OPEN", "IN_PROGRESS", "COMPLETED"})
    @DisplayName("Should retrieve enum value by name")
    void shouldRetrieveEnumValueByName(String statusName) {
        TaskStatus status = TaskStatus.valueOf(statusName);
        assertNotNull(status, "Should retrieve enum value for name: " + statusName);
        assertEquals(statusName, status.name(), "Enum name should match input");
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
        assertEquals(0, TaskStatus.OPEN.ordinal(), "OPEN should have ordinal 0");
        assertEquals(1, TaskStatus.IN_PROGRESS.ordinal(), "IN_PROGRESS should have ordinal 1");
        assertEquals(2, TaskStatus.COMPLETED.ordinal(), "COMPLETED should have ordinal 2");
    }

    /**
     * Test enum string representation.
     * 
     * Equivalence Class: Valid enum values
     * Boundary Value: Each enum value
     */
    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    @DisplayName("Should have correct string representation")
    void shouldHaveCorrectStringRepresentation(TaskStatus status) {
        assertNotNull(status.toString(), "toString() should not return null");
        assertEquals(status.name(), status.toString(), "toString() should return enum name");
    }

    /**
     * Test enum equality and identity.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value compared to itself
     */
    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself(TaskStatus status) {
        assertEquals(status, status, "Enum should be equal to itself");
        assertSame(status, status, "Enum should be identical to itself");
    }

    /**
     * Test enum inequality.
     * 
     * Equivalence Class: Different enum values
     * Boundary Value: Each enum value compared to others
     */
    @Test
    @DisplayName("Should not be equal to different statuses")
    void shouldNotBeEqualToDifferentStatuses() {
        assertNotEquals(TaskStatus.OPEN, TaskStatus.IN_PROGRESS, "OPEN should not equal IN_PROGRESS");
        assertNotEquals(TaskStatus.OPEN, TaskStatus.COMPLETED, "OPEN should not equal COMPLETED");
        assertNotEquals(TaskStatus.IN_PROGRESS, TaskStatus.COMPLETED, "IN_PROGRESS should not equal COMPLETED");
    }

    /**
     * Test enum hash code consistency.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value
     */
    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    @DisplayName("Should have consistent hash code")
    void shouldHaveConsistentHashCode(TaskStatus status) {
        int hashCode1 = status.hashCode();
        int hashCode2 = status.hashCode();
        
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
        assertTrue(TaskStatus.OPEN.compareTo(TaskStatus.IN_PROGRESS) < 0, "OPEN should be less than IN_PROGRESS");
        assertTrue(TaskStatus.OPEN.compareTo(TaskStatus.COMPLETED) < 0, "OPEN should be less than COMPLETED");
        assertTrue(TaskStatus.IN_PROGRESS.compareTo(TaskStatus.COMPLETED) < 0, "IN_PROGRESS should be less than COMPLETED");
        assertTrue(TaskStatus.IN_PROGRESS.compareTo(TaskStatus.OPEN) > 0, "IN_PROGRESS should be greater than OPEN");
        assertTrue(TaskStatus.COMPLETED.compareTo(TaskStatus.OPEN) > 0, "COMPLETED should be greater than OPEN");
        assertTrue(TaskStatus.COMPLETED.compareTo(TaskStatus.IN_PROGRESS) > 0, "COMPLETED should be greater than IN_PROGRESS");
    }

    /**
     * Test enum self-comparison.
     * 
     * Equivalence Class: Same enum values
     * Boundary Value: Each enum value compared to itself
     */
    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    @DisplayName("Should compare equal to itself")
    void shouldCompareEqualToItself(TaskStatus status) {
        assertEquals(0, status.compareTo(status), "Enum should compare equal to itself");
    }

    /**
     * Test invalid enum name handling.
     * 
     * Equivalence Class: Invalid enum names
     * Boundary Value: Non-existent enum names
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "INVALID", "open", "in_progress", "completed", "PENDING", "DONE"})
    @DisplayName("Should throw exception for invalid enum names")
    void shouldThrowExceptionForInvalidEnumNames(String invalidName) {
        assertThrows(IllegalArgumentException.class, 
                    () -> TaskStatus.valueOf(invalidName),
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
        TaskStatus[] values1 = TaskStatus.values();
        TaskStatus[] values2 = TaskStatus.values();
        
        assertNotSame(values1, values2, "values() should return new array each time");
        assertArrayEquals(values1, values2, "values() should return consistent content");
    }
}
