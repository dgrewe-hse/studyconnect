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

import de.hse.swt.studyconnect.enums.GroupRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GroupMember entity.
 * 
 * Tests the GroupMember entity using equivalence class partitioning and boundary value analysis.
 * Covers constructor behavior, getter/setter methods, equality, hash code, and toString.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("GroupMember Entity Tests")
class GroupMemberTest {

    private GroupMember groupMember;
    private Group group;
    private User user;
    private User inviter;
    private final GroupRole VALID_ROLE = GroupRole.STUDENT;

    @BeforeEach
    void setUp() {
        groupMember = new GroupMember();
        group = new Group("Test Group", "Test Description", de.hse.swt.studyconnect.enums.GroupVisibility.PRIVATE, new User());
        user = new User("user@example.com", "password", "User");
        inviter = new User("inviter@example.com", "password", "Inviter");
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create group member with default values")
        void defaultConstructorShouldCreateGroupMemberWithDefaultValues() {
            GroupMember newGroupMember = new GroupMember();
            
            assertNotNull(newGroupMember.getJoinedAt(), "JoinedAt should be set");
        }

        @Test
        @DisplayName("Parameterized constructor should set all fields correctly")
        void parameterizedConstructorShouldSetAllFieldsCorrectly() {
            GroupMember newGroupMember = new GroupMember(group, user, VALID_ROLE, inviter);
            
            assertEquals(group, newGroupMember.getGroup(), "Group should be set");
            assertEquals(user, newGroupMember.getUser(), "User should be set");
            assertEquals(VALID_ROLE, newGroupMember.getRole(), "Role should be set");
            assertEquals(inviter, newGroupMember.getInvitedBy(), "InvitedBy should be set");
            assertNotNull(newGroupMember.getJoinedAt(), "JoinedAt should be set");
        }

        @Test
        @DisplayName("Parameterized constructor should handle null inviter")
        void parameterizedConstructorShouldHandleNullInviter() {
            GroupMember newGroupMember = new GroupMember(group, user, VALID_ROLE, null);
            
            assertEquals(group, newGroupMember.getGroup(), "Group should be set");
            assertEquals(user, newGroupMember.getUser(), "User should be set");
            assertEquals(VALID_ROLE, newGroupMember.getRole(), "Role should be set");
            assertNull(newGroupMember.getInvitedBy(), "InvitedBy should be null");
            assertNotNull(newGroupMember.getJoinedAt(), "JoinedAt should be set");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should get and set ID correctly")
        void shouldGetAndSetIdCorrectly() {
            Long testId = 1L;
            
            groupMember.setId(testId);
            assertEquals(testId, groupMember.getId(), "ID should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set group correctly")
        void shouldGetAndSetGroupCorrectly() {
            groupMember.setGroup(group);
            assertEquals(group, groupMember.getGroup(), "Group should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set user correctly")
        void shouldGetAndSetUserCorrectly() {
            groupMember.setUser(user);
            assertEquals(user, groupMember.getUser(), "User should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set role correctly")
        void shouldGetAndSetRoleCorrectly() {
            groupMember.setRole(VALID_ROLE);
            assertEquals(VALID_ROLE, groupMember.getRole(), "Role should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set joined at correctly")
        void shouldGetAndSetJoinedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            groupMember.setJoinedAt(testTime);
            assertEquals(testTime, groupMember.getJoinedAt(), "JoinedAt should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set invited by correctly")
        void shouldGetAndSetInvitedByCorrectly() {
            groupMember.setInvitedBy(inviter);
            assertEquals(inviter, groupMember.getInvitedBy(), "InvitedBy should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should handle null invited by")
        void shouldHandleNullInvitedBy() {
            groupMember.setInvitedBy(null);
            assertNull(groupMember.getInvitedBy(), "InvitedBy should be null");
        }
    }

    @Nested
    @DisplayName("Role Check Tests")
    class RoleCheckTests {

        @Test
        @DisplayName("Should correctly identify administrator role")
        void shouldCorrectlyIdentifyAdministratorRole() {
            groupMember.setRole(GroupRole.ADMIN);
            assertTrue(groupMember.isAdministrator(), "Should identify as administrator");
            assertFalse(groupMember.isStudent(), "Should not identify as student");
        }

        @Test
        @DisplayName("Should correctly identify student role")
        void shouldCorrectlyIdentifyStudentRole() {
            groupMember.setRole(GroupRole.STUDENT);
            assertTrue(groupMember.isStudent(), "Should identify as student");
            assertFalse(groupMember.isAdministrator(), "Should not identify as administrator");
        }

        @ParameterizedTest
        @EnumSource(GroupRole.class)
        @DisplayName("Should handle all role types correctly")
        void shouldHandleAllRoleTypesCorrectly(GroupRole role) {
            groupMember.setRole(role);
            assertEquals(role, groupMember.getRole(), "Role should be set correctly");
            
            if (role == GroupRole.ADMIN) {
                assertTrue(groupMember.isAdministrator(), "Should identify as administrator");
                assertFalse(groupMember.isStudent(), "Should not identify as student");
            } else if (role == GroupRole.STUDENT) {
                assertTrue(groupMember.isStudent(), "Should identify as student");
                assertFalse(groupMember.isAdministrator(), "Should not identify as administrator");
            }
        }
    }

    @Nested
    @DisplayName("Equality and Hash Code Tests")
    class EqualityTests {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(groupMember, groupMember, "GroupMember should be equal to itself");
            assertEquals(groupMember.hashCode(), groupMember.hashCode(), "Hash code should be consistent");
        }

        @Test
        @DisplayName("Should not be equal to different group member instances")
        void shouldNotBeEqualToDifferentGroupMemberInstances() {
            GroupMember member1 = new GroupMember(group, user, VALID_ROLE, inviter);
            GroupMember member2 = new GroupMember(group, user, VALID_ROLE, inviter);
            
            // Entities typically don't implement custom equality - they use object identity
            // This test verifies that different instances are not equal even with same values
            assertNotEquals(member1, member2, "Different group member instances should not be equal");
            assertNotEquals(member1.hashCode(), member2.hashCode(), "Hash codes should be different");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(groupMember, null, "GroupMember should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different object type")
        void shouldNotBeEqualToDifferentObjectType() {
            String differentObject = "not a group member";
            assertNotEquals(groupMember, differentObject, "GroupMember should not be equal to different object type");
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should include all fields in toString")
        void shouldIncludeAllFieldsInToString() {
            groupMember.setId(1L);
            groupMember.setGroup(group);
            groupMember.setUser(user);
            groupMember.setRole(VALID_ROLE);
            groupMember.setJoinedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
            groupMember.setInvitedBy(inviter);
            
            String toString = groupMember.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("1"), "toString should contain ID");
            assertTrue(toString.contains("Test Group"), "toString should contain group name");
            assertTrue(toString.contains("User"), "toString should contain user name");
            assertTrue(toString.contains(VALID_ROLE.toString()), "toString should contain role");
            assertTrue(toString.contains("2023-01-01T12:00"), "toString should contain joinedAt");
            assertTrue(toString.contains("Inviter"), "toString should contain inviter name");
        }

        @Test
        @DisplayName("Should handle null fields in toString")
        void shouldHandleNullFieldsInToString() {
            String toString = groupMember.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("null"), "toString should contain null for unset fields");
        }
    }

    @Nested
    @DisplayName("Boundary Value Tests")
    class BoundaryValueTests {

        @Test
        @DisplayName("Should handle minimum ID value")
        void shouldHandleMinimumIdValue() {
            Long minId = 1L;
            groupMember.setId(minId);
            assertEquals(minId, groupMember.getId(), "Should handle minimum ID value");
        }

        @Test
        @DisplayName("Should handle maximum ID value")
        void shouldHandleMaximumIdValue() {
            Long maxId = Long.MAX_VALUE;
            groupMember.setId(maxId);
            assertEquals(maxId, groupMember.getId(), "Should handle maximum ID value");
        }

        @Test
        @DisplayName("Should handle minimum timestamp")
        void shouldHandleMinimumTimestamp() {
            LocalDateTime minTime = LocalDateTime.MIN;
            groupMember.setJoinedAt(minTime);
            assertEquals(minTime, groupMember.getJoinedAt(), "Should handle minimum timestamp");
        }

        @Test
        @DisplayName("Should handle maximum timestamp")
        void shouldHandleMaximumTimestamp() {
            LocalDateTime maxTime = LocalDateTime.MAX;
            groupMember.setJoinedAt(maxTime);
            assertEquals(maxTime, groupMember.getJoinedAt(), "Should handle maximum timestamp");
        }

        @Test
        @DisplayName("Should handle current timestamp")
        void shouldHandleCurrentTimestamp() {
            LocalDateTime currentTime = LocalDateTime.now();
            groupMember.setJoinedAt(currentTime);
            assertEquals(currentTime, groupMember.getJoinedAt(), "Should handle current timestamp");
        }
    }

    @Nested
    @DisplayName("Null Value Tests")
    class NullValueTests {

        @Test
        @DisplayName("Should handle null ID")
        void shouldHandleNullId() {
            groupMember.setId(null);
            assertNull(groupMember.getId(), "Should handle null ID");
        }

        @Test
        @DisplayName("Should handle null group")
        void shouldHandleNullGroup() {
            groupMember.setGroup(null);
            assertNull(groupMember.getGroup(), "Should handle null group");
        }

        @Test
        @DisplayName("Should handle null user")
        void shouldHandleNullUser() {
            groupMember.setUser(null);
            assertNull(groupMember.getUser(), "Should handle null user");
        }

        @Test
        @DisplayName("Should handle null role")
        void shouldHandleNullRole() {
            groupMember.setRole(null);
            assertNull(groupMember.getRole(), "Should handle null role");
        }

        @Test
        @DisplayName("Should handle null joined at")
        void shouldHandleNullJoinedAt() {
            groupMember.setJoinedAt(null);
            assertNull(groupMember.getJoinedAt(), "Should handle null joinedAt");
        }

        @Test
        @DisplayName("Should handle null invited by")
        void shouldHandleNullInvitedBy() {
            groupMember.setInvitedBy(null);
            assertNull(groupMember.getInvitedBy(), "Should handle null invitedBy");
        }
    }
}
