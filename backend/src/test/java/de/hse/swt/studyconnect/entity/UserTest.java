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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User entity.
 * 
 * Tests the User entity using equivalence class partitioning and boundary value analysis.
 * Covers constructor behavior, getter/setter methods, equality, hash code, and toString.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("User Entity Tests")
class UserTest {

    private User user;
    private final String VALID_EMAIL = "test@example.com";
    private final String VALID_PASSWORD_HASH = "hashedPassword123";
    private final String VALID_NAME = "John Doe";

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create user with default values")
        void defaultConstructorShouldCreateUserWithDefaultValues() {
            User newUser = new User();
            
            assertNotNull(newUser.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newUser.getUpdatedAt(), "UpdatedAt should be set");
            assertNotNull(newUser.getPersonalTasks(), "PersonalTasks should be initialized");
            assertNotNull(newUser.getGroupMemberships(), "GroupMemberships should be initialized");
            assertNotNull(newUser.getComments(), "Comments should be initialized");
            assertTrue(newUser.getPersonalTasks().isEmpty(), "PersonalTasks should be empty");
            assertTrue(newUser.getGroupMemberships().isEmpty(), "GroupMemberships should be empty");
            assertTrue(newUser.getComments().isEmpty(), "Comments should be empty");
        }

        @Test
        @DisplayName("Parameterized constructor should set all fields correctly")
        void parameterizedConstructorShouldSetAllFieldsCorrectly() {
            User newUser = new User(VALID_EMAIL, VALID_PASSWORD_HASH, VALID_NAME);
            
            assertEquals(VALID_EMAIL, newUser.getEmail(), "Email should be set");
            assertEquals(VALID_PASSWORD_HASH, newUser.getPasswordHash(), "PasswordHash should be set");
            assertEquals(VALID_NAME, newUser.getName(), "Name should be set");
            assertNotNull(newUser.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newUser.getUpdatedAt(), "UpdatedAt should be set");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should get and set ID correctly")
        void shouldGetAndSetIdCorrectly() {
            Long testId = 1L;
            
            user.setId(testId);
            assertEquals(testId, user.getId(), "ID should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set email correctly")
        void shouldGetAndSetEmailCorrectly() {
            user.setEmail(VALID_EMAIL);
            assertEquals(VALID_EMAIL, user.getEmail(), "Email should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set password hash correctly")
        void shouldGetAndSetPasswordHashCorrectly() {
            user.setPasswordHash(VALID_PASSWORD_HASH);
            assertEquals(VALID_PASSWORD_HASH, user.getPasswordHash(), "PasswordHash should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set name correctly")
        void shouldGetAndSetNameCorrectly() {
            user.setName(VALID_NAME);
            assertEquals(VALID_NAME, user.getName(), "Name should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set created at correctly")
        void shouldGetAndSetCreatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            user.setCreatedAt(testTime);
            assertEquals(testTime, user.getCreatedAt(), "CreatedAt should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set updated at correctly")
        void shouldGetAndSetUpdatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            user.setUpdatedAt(testTime);
            assertEquals(testTime, user.getUpdatedAt(), "UpdatedAt should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set last login correctly")
        void shouldGetAndSetLastLoginCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            user.setLastLogin(testTime);
            assertEquals(testTime, user.getLastLogin(), "LastLogin should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should handle null last login")
        void shouldHandleNullLastLogin() {
            user.setLastLogin(null);
            assertNull(user.getLastLogin(), "LastLogin should be null");
        }
    }

    @Nested
    @DisplayName("Collection Tests")
    class CollectionTests {

        @Test
        @DisplayName("Should get and set personal tasks correctly")
        void shouldGetAndSetPersonalTasksCorrectly() {
            List<Task> tasks = new ArrayList<>();
            Task task1 = new Task();
            Task task2 = new Task();
            tasks.add(task1);
            tasks.add(task2);
            
            user.setPersonalTasks(tasks);
            assertEquals(tasks, user.getPersonalTasks(), "PersonalTasks should be set and retrieved correctly");
            assertEquals(2, user.getPersonalTasks().size(), "PersonalTasks should contain 2 tasks");
        }

        @Test
        @DisplayName("Should get and set group memberships correctly")
        void shouldGetAndSetGroupMembershipsCorrectly() {
            List<GroupMember> memberships = new ArrayList<>();
            GroupMember membership1 = new GroupMember();
            GroupMember membership2 = new GroupMember();
            memberships.add(membership1);
            memberships.add(membership2);
            
            user.setGroupMemberships(memberships);
            assertEquals(memberships, user.getGroupMemberships(), "GroupMemberships should be set and retrieved correctly");
            assertEquals(2, user.getGroupMemberships().size(), "GroupMemberships should contain 2 memberships");
        }

        @Test
        @DisplayName("Should get and set comments correctly")
        void shouldGetAndSetCommentsCorrectly() {
            List<Comment> comments = new ArrayList<>();
            Comment comment1 = new Comment();
            Comment comment2 = new Comment();
            comments.add(comment1);
            comments.add(comment2);
            
            user.setComments(comments);
            assertEquals(comments, user.getComments(), "Comments should be set and retrieved correctly");
            assertEquals(2, user.getComments().size(), "Comments should contain 2 comments");
        }

        @Test
        @DisplayName("Should handle null collections")
        void shouldHandleNullCollections() {
            user.setPersonalTasks(null);
            user.setGroupMemberships(null);
            user.setComments(null);
            
            assertNull(user.getPersonalTasks(), "PersonalTasks should be null");
            assertNull(user.getGroupMemberships(), "GroupMemberships should be null");
            assertNull(user.getComments(), "Comments should be null");
        }
    }

    @Nested
    @DisplayName("Equality and Hash Code Tests")
    class EqualityTests {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(user, user, "User should be equal to itself");
            assertEquals(user.hashCode(), user.hashCode(), "Hash code should be consistent");
        }

        @Test
        @DisplayName("Should not be equal to different user instances")
        void shouldNotBeEqualToDifferentUserInstances() {
            User user1 = new User(VALID_EMAIL, VALID_PASSWORD_HASH, VALID_NAME);
            User user2 = new User(VALID_EMAIL, VALID_PASSWORD_HASH, VALID_NAME);
            
            // Entities typically don't implement custom equality - they use object identity
            // This test verifies that different instances are not equal even with same values
            assertNotEquals(user1, user2, "Different user instances should not be equal");
            assertNotEquals(user1.hashCode(), user2.hashCode(), "Hash codes should be different");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(user, null, "User should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different object type")
        void shouldNotBeEqualToDifferentObjectType() {
            String differentObject = "not a user";
            assertNotEquals(user, differentObject, "User should not be equal to different object type");
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should include all fields in toString")
        void shouldIncludeAllFieldsInToString() {
            user.setId(1L);
            user.setEmail(VALID_EMAIL);
            user.setName(VALID_NAME);
            user.setCreatedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
            user.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 12, 0));
            user.setLastLogin(LocalDateTime.of(2023, 1, 3, 12, 0));
            
            String toString = user.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("1"), "toString should contain ID");
            assertTrue(toString.contains(VALID_EMAIL), "toString should contain email");
            assertTrue(toString.contains(VALID_NAME), "toString should contain name");
            assertTrue(toString.contains("2023-01-01T12:00"), "toString should contain createdAt");
            assertTrue(toString.contains("2023-01-02T12:00"), "toString should contain updatedAt");
            assertTrue(toString.contains("2023-01-03T12:00"), "toString should contain lastLogin");
        }

        @Test
        @DisplayName("Should handle null fields in toString")
        void shouldHandleNullFieldsInToString() {
            String toString = user.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("null"), "toString should contain null for unset fields");
        }
    }

    @Nested
    @DisplayName("Boundary Value Tests")
    class BoundaryValueTests {

        @ParameterizedTest
        @ValueSource(strings = {"a@b.c", "test@example.com", "very.long.email.address@very.long.domain.name.com"})
        @DisplayName("Should handle various email formats")
        void shouldHandleVariousEmailFormats(String email) {
            user.setEmail(email);
            assertEquals(email, user.getEmail(), "Should handle email: " + email);
        }

        @ParameterizedTest
        @ValueSource(strings = {"A", "John Doe", "Very Long Name That Exceeds Normal Length But Is Still Valid"})
        @DisplayName("Should handle various name lengths")
        void shouldHandleVariousNameLengths(String name) {
            user.setName(name);
            assertEquals(name, user.getName(), "Should handle name: " + name);
        }

        @ParameterizedTest
        @ValueSource(strings = {"a", "short", "very_long_password_hash_that_might_be_generated_by_bcrypt_or_argon2"})
        @DisplayName("Should handle various password hash lengths")
        void shouldHandleVariousPasswordHashLengths(String passwordHash) {
            user.setPasswordHash(passwordHash);
            assertEquals(passwordHash, user.getPasswordHash(), "Should handle password hash: " + passwordHash);
        }

        @Test
        @DisplayName("Should handle minimum ID value")
        void shouldHandleMinimumIdValue() {
            Long minId = 1L;
            user.setId(minId);
            assertEquals(minId, user.getId(), "Should handle minimum ID value");
        }

        @Test
        @DisplayName("Should handle maximum ID value")
        void shouldHandleMaximumIdValue() {
            Long maxId = Long.MAX_VALUE;
            user.setId(maxId);
            assertEquals(maxId, user.getId(), "Should handle maximum ID value");
        }
    }

    @Nested
    @DisplayName("Null Value Tests")
    class NullValueTests {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty email")
        void shouldHandleNullAndEmptyEmail(String email) {
            user.setEmail(email);
            assertEquals(email, user.getEmail(), "Should handle null/empty email");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty password hash")
        void shouldHandleNullAndEmptyPasswordHash(String passwordHash) {
            user.setPasswordHash(passwordHash);
            assertEquals(passwordHash, user.getPasswordHash(), "Should handle null/empty password hash");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty name")
        void shouldHandleNullAndEmptyName(String name) {
            user.setName(name);
            assertEquals(name, user.getName(), "Should handle null/empty name");
        }

        @Test
        @DisplayName("Should handle null ID")
        void shouldHandleNullId() {
            user.setId(null);
            assertNull(user.getId(), "Should handle null ID");
        }

        @Test
        @DisplayName("Should handle null timestamps")
        void shouldHandleNullTimestamps() {
            user.setCreatedAt(null);
            user.setUpdatedAt(null);
            user.setLastLogin(null);
            
            assertNull(user.getCreatedAt(), "Should handle null createdAt");
            assertNull(user.getUpdatedAt(), "Should handle null updatedAt");
            assertNull(user.getLastLogin(), "Should handle null lastLogin");
        }
    }
}
