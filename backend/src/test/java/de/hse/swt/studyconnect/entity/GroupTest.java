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

import de.hse.swt.studyconnect.enums.GroupVisibility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Group entity.
 * 
 * Tests the Group entity using equivalence class partitioning and boundary value analysis.
 * Covers constructor behavior, getter/setter methods, equality, hash code, and toString.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("Group Entity Tests")
class GroupTest {

    private Group group;
    private User creator;
    private final String VALID_NAME = "Study Group";
    private final String VALID_DESCRIPTION = "A study group for computer science";
    private final GroupVisibility VALID_VISIBILITY = GroupVisibility.PRIVATE;
    private final Integer VALID_MAX_MEMBERS = 20;

    @BeforeEach
    void setUp() {
        group = new Group();
        creator = new User("creator@example.com", "password", "Creator");
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create group with default values")
        void defaultConstructorShouldCreateGroupWithDefaultValues() {
            Group newGroup = new Group();
            
            assertNotNull(newGroup.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newGroup.getUpdatedAt(), "UpdatedAt should be set");
            assertNotNull(newGroup.getMembers(), "Members should be initialized");
            assertNotNull(newGroup.getGroupTasks(), "GroupTasks should be initialized");
            assertTrue(newGroup.getMembers().isEmpty(), "Members should be empty");
            assertTrue(newGroup.getGroupTasks().isEmpty(), "GroupTasks should be empty");
            assertEquals(20, newGroup.getMaxMembers(), "Default maxMembers should be 20");
        }

        @Test
        @DisplayName("Parameterized constructor should set all fields correctly")
        void parameterizedConstructorShouldSetAllFieldsCorrectly() {
            Group newGroup = new Group(VALID_NAME, VALID_DESCRIPTION, VALID_VISIBILITY, creator);
            
            assertEquals(VALID_NAME, newGroup.getName(), "Name should be set");
            assertEquals(VALID_DESCRIPTION, newGroup.getDescription(), "Description should be set");
            assertEquals(VALID_VISIBILITY, newGroup.getVisibility(), "Visibility should be set");
            assertEquals(creator, newGroup.getCreatedBy(), "CreatedBy should be set");
            assertNotNull(newGroup.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newGroup.getUpdatedAt(), "UpdatedAt should be set");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should get and set ID correctly")
        void shouldGetAndSetIdCorrectly() {
            Long testId = 1L;
            
            group.setId(testId);
            assertEquals(testId, group.getId(), "ID should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set name correctly")
        void shouldGetAndSetNameCorrectly() {
            group.setName(VALID_NAME);
            assertEquals(VALID_NAME, group.getName(), "Name should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set description correctly")
        void shouldGetAndSetDescriptionCorrectly() {
            group.setDescription(VALID_DESCRIPTION);
            assertEquals(VALID_DESCRIPTION, group.getDescription(), "Description should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set visibility correctly")
        void shouldGetAndSetVisibilityCorrectly() {
            group.setVisibility(VALID_VISIBILITY);
            assertEquals(VALID_VISIBILITY, group.getVisibility(), "Visibility should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set max members correctly")
        void shouldGetAndSetMaxMembersCorrectly() {
            group.setMaxMembers(VALID_MAX_MEMBERS);
            assertEquals(VALID_MAX_MEMBERS, group.getMaxMembers(), "MaxMembers should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set created by correctly")
        void shouldGetAndSetCreatedByCorrectly() {
            group.setCreatedBy(creator);
            assertEquals(creator, group.getCreatedBy(), "CreatedBy should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set created at correctly")
        void shouldGetAndSetCreatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            group.setCreatedAt(testTime);
            assertEquals(testTime, group.getCreatedAt(), "CreatedAt should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set updated at correctly")
        void shouldGetAndSetUpdatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            group.setUpdatedAt(testTime);
            assertEquals(testTime, group.getUpdatedAt(), "UpdatedAt should be set and retrieved correctly");
        }
    }

    @Nested
    @DisplayName("Collection Tests")
    class CollectionTests {

        @Test
        @DisplayName("Should get and set members correctly")
        void shouldGetAndSetMembersCorrectly() {
            List<GroupMember> members = new ArrayList<>();
            GroupMember member1 = new GroupMember();
            GroupMember member2 = new GroupMember();
            members.add(member1);
            members.add(member2);
            
            group.setMembers(members);
            assertEquals(members, group.getMembers(), "Members should be set and retrieved correctly");
            assertEquals(2, group.getMembers().size(), "Members should contain 2 members");
        }

        @Test
        @DisplayName("Should get and set group tasks correctly")
        void shouldGetAndSetGroupTasksCorrectly() {
            List<Task> tasks = new ArrayList<>();
            Task task1 = new Task();
            Task task2 = new Task();
            tasks.add(task1);
            tasks.add(task2);
            
            group.setGroupTasks(tasks);
            assertEquals(tasks, group.getGroupTasks(), "GroupTasks should be set and retrieved correctly");
            assertEquals(2, group.getGroupTasks().size(), "GroupTasks should contain 2 tasks");
        }

        @Test
        @DisplayName("Should handle null collections")
        void shouldHandleNullCollections() {
            group.setMembers(null);
            group.setGroupTasks(null);
            
            assertNull(group.getMembers(), "Members should be null");
            assertNull(group.getGroupTasks(), "GroupTasks should be null");
        }
    }

    @Nested
    @DisplayName("Equality and Hash Code Tests")
    class EqualityTests {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(group, group, "Group should be equal to itself");
            assertEquals(group.hashCode(), group.hashCode(), "Hash code should be consistent");
        }

        @Test
        @DisplayName("Should not be equal to different group instances")
        void shouldNotBeEqualToDifferentGroupInstances() {
            Group group1 = new Group(VALID_NAME, VALID_DESCRIPTION, VALID_VISIBILITY, creator);
            Group group2 = new Group(VALID_NAME, VALID_DESCRIPTION, VALID_VISIBILITY, creator);
            
            // Entities typically don't implement custom equality - they use object identity
            // This test verifies that different instances are not equal even with same values
            assertNotEquals(group1, group2, "Different group instances should not be equal");
            assertNotEquals(group1.hashCode(), group2.hashCode(), "Hash codes should be different");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(group, null, "Group should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different object type")
        void shouldNotBeEqualToDifferentObjectType() {
            String differentObject = "not a group";
            assertNotEquals(group, differentObject, "Group should not be equal to different object type");
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should include all fields in toString")
        void shouldIncludeAllFieldsInToString() {
            group.setId(1L);
            group.setName(VALID_NAME);
            group.setDescription(VALID_DESCRIPTION);
            group.setVisibility(VALID_VISIBILITY);
            group.setMaxMembers(VALID_MAX_MEMBERS);
            group.setCreatedBy(creator);
            group.setCreatedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
            group.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 12, 0));
            
            String toString = group.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("1"), "toString should contain ID");
            assertTrue(toString.contains(VALID_NAME), "toString should contain name");
            assertTrue(toString.contains(VALID_DESCRIPTION), "toString should contain description");
            assertTrue(toString.contains(VALID_VISIBILITY.toString()), "toString should contain visibility");
            assertTrue(toString.contains(VALID_MAX_MEMBERS.toString()), "toString should contain maxMembers");
            assertTrue(toString.contains("Creator"), "toString should contain creator name");
            assertTrue(toString.contains("2023-01-01T12:00"), "toString should contain createdAt");
            assertTrue(toString.contains("2023-01-02T12:00"), "toString should contain updatedAt");
        }

        @Test
        @DisplayName("Should handle null fields in toString")
        void shouldHandleNullFieldsInToString() {
            String toString = group.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("null"), "toString should contain null for unset fields");
        }
    }

    @Nested
    @DisplayName("Boundary Value Tests")
    class BoundaryValueTests {

        @ParameterizedTest
        @ValueSource(strings = {"A", "Study Group", "Very Long Group Name That Exceeds Normal Length But Is Still Valid"})
        @DisplayName("Should handle various name lengths")
        void shouldHandleVariousNameLengths(String name) {
            group.setName(name);
            assertEquals(name, group.getName(), "Should handle name: " + name);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "Short description", "Very long description that might exceed normal length but is still valid for testing purposes"})
        @DisplayName("Should handle various description lengths")
        void shouldHandleVariousDescriptionLengths(String description) {
            group.setDescription(description);
            assertEquals(description, group.getDescription(), "Should handle description: " + description);
        }

        @ParameterizedTest
        @EnumSource(GroupVisibility.class)
        @DisplayName("Should handle all visibility types")
        void shouldHandleAllVisibilityTypes(GroupVisibility visibility) {
            group.setVisibility(visibility);
            assertEquals(visibility, group.getVisibility(), "Should handle visibility: " + visibility);
        }

        @ParameterizedTest
        @ValueSource(ints = {2, 10, 20, 50})
        @DisplayName("Should handle various max members values")
        void shouldHandleVariousMaxMembersValues(int maxMembers) {
            group.setMaxMembers(maxMembers);
            assertEquals(maxMembers, group.getMaxMembers(), "Should handle maxMembers: " + maxMembers);
        }

        @Test
        @DisplayName("Should handle minimum ID value")
        void shouldHandleMinimumIdValue() {
            Long minId = 1L;
            group.setId(minId);
            assertEquals(minId, group.getId(), "Should handle minimum ID value");
        }

        @Test
        @DisplayName("Should handle maximum ID value")
        void shouldHandleMaximumIdValue() {
            Long maxId = Long.MAX_VALUE;
            group.setId(maxId);
            assertEquals(maxId, group.getId(), "Should handle maximum ID value");
        }
    }

    @Nested
    @DisplayName("Null Value Tests")
    class NullValueTests {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty name")
        void shouldHandleNullAndEmptyName(String name) {
            group.setName(name);
            assertEquals(name, group.getName(), "Should handle null/empty name");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty description")
        void shouldHandleNullAndEmptyDescription(String description) {
            group.setDescription(description);
            assertEquals(description, group.getDescription(), "Should handle null/empty description");
        }

        @Test
        @DisplayName("Should handle null ID")
        void shouldHandleNullId() {
            group.setId(null);
            assertNull(group.getId(), "Should handle null ID");
        }

        @Test
        @DisplayName("Should handle null visibility")
        void shouldHandleNullVisibility() {
            group.setVisibility(null);
            assertNull(group.getVisibility(), "Should handle null visibility");
        }

        @Test
        @DisplayName("Should handle null max members")
        void shouldHandleNullMaxMembers() {
            group.setMaxMembers(null);
            assertNull(group.getMaxMembers(), "Should handle null maxMembers");
        }

        @Test
        @DisplayName("Should handle null created by")
        void shouldHandleNullCreatedBy() {
            group.setCreatedBy(null);
            assertNull(group.getCreatedBy(), "Should handle null createdBy");
        }

        @Test
        @DisplayName("Should handle null timestamps")
        void shouldHandleNullTimestamps() {
            group.setCreatedAt(null);
            group.setUpdatedAt(null);
            
            assertNull(group.getCreatedAt(), "Should handle null createdAt");
            assertNull(group.getUpdatedAt(), "Should handle null updatedAt");
        }
    }
}
