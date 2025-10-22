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

package de.hse.swt.studyconnect.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Comment entity.
 * 
 * Tests the Comment entity using equivalence class partitioning and boundary value analysis.
 * Covers constructor behavior, getter/setter methods, equality, hash code, and toString.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("Comment Entity Tests")
class CommentTest {

    private Comment comment;
    private Task task;
    private User user;
    private final String VALID_CONTENT = "This is a test comment";

    @BeforeEach
    void setUp() {
        comment = new Comment();
        task = new Task("Test Task", "Test Description", LocalDateTime.now().plusDays(7), de.hse.swt.studyconnect.enums.TaskPriority.HIGH, new User());
        user = new User("user@example.com", "password", "User");
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create comment with default values")
        void defaultConstructorShouldCreateCommentWithDefaultValues() {
            Comment newComment = new Comment();
            
            assertNotNull(newComment.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newComment.getUpdatedAt(), "UpdatedAt should be set");
        }

        @Test
        @DisplayName("Parameterized constructor should set all fields correctly")
        void parameterizedConstructorShouldSetAllFieldsCorrectly() {
            Comment newComment = new Comment(task, user, VALID_CONTENT);
            
            assertEquals(task, newComment.getTask(), "Task should be set");
            assertEquals(user, newComment.getUser(), "User should be set");
            assertEquals(VALID_CONTENT, newComment.getContent(), "Content should be set");
            assertNotNull(newComment.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newComment.getUpdatedAt(), "UpdatedAt should be set");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should get and set ID correctly")
        void shouldGetAndSetIdCorrectly() {
            Long testId = 1L;
            
            comment.setId(testId);
            assertEquals(testId, comment.getId(), "ID should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set task correctly")
        void shouldGetAndSetTaskCorrectly() {
            comment.setTask(task);
            assertEquals(task, comment.getTask(), "Task should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set user correctly")
        void shouldGetAndSetUserCorrectly() {
            comment.setUser(user);
            assertEquals(user, comment.getUser(), "User should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set content correctly")
        void shouldGetAndSetContentCorrectly() {
            comment.setContent(VALID_CONTENT);
            assertEquals(VALID_CONTENT, comment.getContent(), "Content should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set created at correctly")
        void shouldGetAndSetCreatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            comment.setCreatedAt(testTime);
            assertEquals(testTime, comment.getCreatedAt(), "CreatedAt should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set updated at correctly")
        void shouldGetAndSetUpdatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            comment.setUpdatedAt(testTime);
            assertEquals(testTime, comment.getUpdatedAt(), "UpdatedAt should be set and retrieved correctly");
        }
    }

    @Nested
    @DisplayName("Equality and Hash Code Tests")
    class EqualityTests {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(comment, comment, "Comment should be equal to itself");
            assertEquals(comment.hashCode(), comment.hashCode(), "Hash code should be consistent");
        }

        @Test
        @DisplayName("Should not be equal to different comment instances")
        void shouldNotBeEqualToDifferentCommentInstances() {
            Comment comment1 = new Comment(task, user, VALID_CONTENT);
            Comment comment2 = new Comment(task, user, VALID_CONTENT);
            
            // Entities typically don't implement custom equality - they use object identity
            // This test verifies that different instances are not equal even with same values
            assertNotEquals(comment1, comment2, "Different comment instances should not be equal");
            assertNotEquals(comment1.hashCode(), comment2.hashCode(), "Hash codes should be different");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(comment, null, "Comment should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different object type")
        void shouldNotBeEqualToDifferentObjectType() {
            String differentObject = "not a comment";
            assertNotEquals(comment, differentObject, "Comment should not be equal to different object type");
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should include all fields in toString")
        void shouldIncludeAllFieldsInToString() {
            comment.setId(1L);
            comment.setTask(task);
            comment.setUser(user);
            comment.setContent(VALID_CONTENT);
            comment.setCreatedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
            comment.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 12, 0));
            
            String toString = comment.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("1"), "toString should contain ID");
            assertTrue(toString.contains("Test Task"), "toString should contain task title");
            assertTrue(toString.contains("User"), "toString should contain user name");
            assertTrue(toString.contains(VALID_CONTENT), "toString should contain content");
            assertTrue(toString.contains("2023-01-01T12:00"), "toString should contain createdAt");
            assertTrue(toString.contains("2023-01-02T12:00"), "toString should contain updatedAt");
        }

        @Test
        @DisplayName("Should handle null fields in toString")
        void shouldHandleNullFieldsInToString() {
            String toString = comment.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("null"), "toString should contain null for unset fields");
        }
    }

    @Nested
    @DisplayName("Boundary Value Tests")
    class BoundaryValueTests {

        @ParameterizedTest
        @ValueSource(strings = {"A", "Short comment", "Very long comment that might exceed normal length but is still valid for testing purposes"})
        @DisplayName("Should handle various content lengths")
        void shouldHandleVariousContentLengths(String content) {
            comment.setContent(content);
            assertEquals(content, comment.getContent(), "Should handle content: " + content);
        }

        @Test
        @DisplayName("Should handle minimum ID value")
        void shouldHandleMinimumIdValue() {
            Long minId = 1L;
            comment.setId(minId);
            assertEquals(minId, comment.getId(), "Should handle minimum ID value");
        }

        @Test
        @DisplayName("Should handle maximum ID value")
        void shouldHandleMaximumIdValue() {
            Long maxId = Long.MAX_VALUE;
            comment.setId(maxId);
            assertEquals(maxId, comment.getId(), "Should handle maximum ID value");
        }

        @Test
        @DisplayName("Should handle minimum timestamp")
        void shouldHandleMinimumTimestamp() {
            LocalDateTime minTime = LocalDateTime.MIN;
            comment.setCreatedAt(minTime);
            assertEquals(minTime, comment.getCreatedAt(), "Should handle minimum timestamp");
        }

        @Test
        @DisplayName("Should handle maximum timestamp")
        void shouldHandleMaximumTimestamp() {
            LocalDateTime maxTime = LocalDateTime.MAX;
            comment.setCreatedAt(maxTime);
            assertEquals(maxTime, comment.getCreatedAt(), "Should handle maximum timestamp");
        }

        @Test
        @DisplayName("Should handle current timestamp")
        void shouldHandleCurrentTimestamp() {
            LocalDateTime currentTime = LocalDateTime.now();
            comment.setCreatedAt(currentTime);
            assertEquals(currentTime, comment.getCreatedAt(), "Should handle current timestamp");
        }
    }

    @Nested
    @DisplayName("Null Value Tests")
    class NullValueTests {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty content")
        void shouldHandleNullAndEmptyContent(String content) {
            comment.setContent(content);
            assertEquals(content, comment.getContent(), "Should handle null/empty content");
        }

        @Test
        @DisplayName("Should handle null ID")
        void shouldHandleNullId() {
            comment.setId(null);
            assertNull(comment.getId(), "Should handle null ID");
        }

        @Test
        @DisplayName("Should handle null task")
        void shouldHandleNullTask() {
            comment.setTask(null);
            assertNull(comment.getTask(), "Should handle null task");
        }

        @Test
        @DisplayName("Should handle null user")
        void shouldHandleNullUser() {
            comment.setUser(null);
            assertNull(comment.getUser(), "Should handle null user");
        }

        @Test
        @DisplayName("Should handle null timestamps")
        void shouldHandleNullTimestamps() {
            comment.setCreatedAt(null);
            comment.setUpdatedAt(null);
            
            assertNull(comment.getCreatedAt(), "Should handle null createdAt");
            assertNull(comment.getUpdatedAt(), "Should handle null updatedAt");
        }
    }
}
