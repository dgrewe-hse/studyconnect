package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.GroupRole;
import de.hse.swt.studyconnect.enums.GroupVisibility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for group related DTOs.
 */
@DisplayName("Group DTO Tests")
class GroupDtoTest {

    @Nested
    @DisplayName("GroupCreateRequest Tests")
    class GroupCreateRequestTests {

        @Test
        @DisplayName("Parameterized constructor should set required fields")
        void parameterizedConstructorShouldSetRequiredFields() {
            GroupCreateRequest request = new GroupCreateRequest(
                    "Group",
                    "Description",
                    GroupVisibility.PRIVATE
            );

            assertEquals("Group", request.getName());
            assertEquals("Description", request.getDescription());
            assertEquals(GroupVisibility.PRIVATE, request.getVisibility());
            assertEquals(20, request.getMaxMembers());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            GroupCreateRequest request = new GroupCreateRequest();

            request.setName("Group");
            request.setDescription("Description");
            request.setVisibility(GroupVisibility.PUBLIC);
            request.setMaxMembers(30);

            assertEquals("Group", request.getName());
            assertEquals("Description", request.getDescription());
            assertEquals(GroupVisibility.PUBLIC, request.getVisibility());
            assertEquals(30, request.getMaxMembers());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("GroupUpdateRequest Tests")
    class GroupUpdateRequestTests {

        @Test
        @DisplayName("Should get and set all mutable properties")
        void shouldGetAndSetAllMutableProperties() {
            GroupUpdateRequest request = new GroupUpdateRequest();

            request.setName("Updated Group");
            request.setDescription("Updated Description");
            request.setVisibility(GroupVisibility.PUBLIC);
            request.setMaxMembers(25);

            assertEquals("Updated Group", request.getName());
            assertEquals("Updated Description", request.getDescription());
            assertEquals(GroupVisibility.PUBLIC, request.getVisibility());
            assertEquals(25, request.getMaxMembers());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("GroupMemberAddRequest Tests")
    class GroupMemberAddRequestTests {

        @Test
        @DisplayName("Convenience constructor should set fields")
        void convenienceConstructorShouldSetFields() {
            GroupMemberAddRequest request = new GroupMemberAddRequest(5L, GroupRole.STUDENT);

            assertEquals(5L, request.getUserId());
            assertEquals(GroupRole.STUDENT, request.getRole());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            GroupMemberAddRequest request = new GroupMemberAddRequest();

            request.setUserId(10L);
            request.setRole(GroupRole.ADMIN);

            assertEquals(10L, request.getUserId());
            assertEquals(GroupRole.ADMIN, request.getRole());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("GroupAdminTransferRequest Tests")
    class GroupAdminTransferRequestTests {

        @Test
        @DisplayName("Convenience constructor should set new admin user id")
        void convenienceConstructorShouldSetNewAdminUserId() {
            GroupAdminTransferRequest request = new GroupAdminTransferRequest(7L);
            assertEquals(7L, request.getNewAdminUserId());
        }

        @Test
        @DisplayName("Getter and setter should work for new admin user id")
        void getterAndSetterShouldWorkForNewAdminUserId() {
            GroupAdminTransferRequest request = new GroupAdminTransferRequest();
            request.setNewAdminUserId(8L);

            assertEquals(8L, request.getNewAdminUserId());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("GroupSummary Tests")
    class GroupSummaryTests {

        @Test
        @DisplayName("Constructor should set all fields")
        void constructorShouldSetAllFields() {
            GroupSummary summary = new GroupSummary(1L, "Group", "Description", GroupVisibility.PRIVATE);

            assertEquals(1L, summary.getId());
            assertEquals("Group", summary.getName());
            assertEquals("Description", summary.getDescription());
            assertEquals(GroupVisibility.PRIVATE, summary.getVisibility());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            GroupSummary summary = new GroupSummary();

            summary.setId(2L);
            summary.setName("Group2");
            summary.setDescription("Description2");
            summary.setVisibility(GroupVisibility.PUBLIC);

            assertEquals(2L, summary.getId());
            assertEquals("Group2", summary.getName());
            assertEquals("Description2", summary.getDescription());
            assertEquals(GroupVisibility.PUBLIC, summary.getVisibility());
            assertNotNull(summary.toString());
        }
    }

    @Nested
    @DisplayName("GroupMemberResponse Tests")
    class GroupMemberResponseTests {

        @Test
        @DisplayName("Constructor should set all fields")
        void constructorShouldSetAllFields() {
            UserSummary user = new UserSummary(1L, "User");
            UserSummary inviter = new UserSummary(2L, "Inviter");
            LocalDateTime joinedAt = LocalDateTime.of(2025, 1, 1, 10, 0);
            GroupMemberResponse response = new GroupMemberResponse(5L, user, GroupRole.STUDENT, joinedAt, inviter);

            assertEquals(5L, response.getId());
            assertEquals(user, response.getUser());
            assertEquals(GroupRole.STUDENT, response.getRole());
            assertEquals(joinedAt, response.getJoinedAt());
            assertEquals(inviter, response.getInvitedBy());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            GroupMemberResponse response = new GroupMemberResponse();
            UserSummary user = new UserSummary(3L, "User");
            UserSummary inviter = new UserSummary(4L, "Inviter");
            LocalDateTime joinedAt = LocalDateTime.of(2025, 2, 2, 11, 0);

            response.setId(6L);
            response.setUser(user);
            response.setRole(GroupRole.ADMIN);
            response.setJoinedAt(joinedAt);
            response.setInvitedBy(inviter);

            assertEquals(6L, response.getId());
            assertEquals(user, response.getUser());
            assertEquals(GroupRole.ADMIN, response.getRole());
            assertEquals(joinedAt, response.getJoinedAt());
            assertEquals(inviter, response.getInvitedBy());
            assertNotNull(response.toString());
        }
    }

    @Nested
    @DisplayName("GroupResponse Tests")
    class GroupResponseTests {

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            GroupResponse response = new GroupResponse();
            UserSummary creator = new UserSummary(1L, "Creator");
            LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 10, 0);
            LocalDateTime updatedAt = LocalDateTime.of(2025, 1, 2, 11, 0);

            response.setId(10L);
            response.setName("Group");
            response.setDescription("Description");
            response.setVisibility(GroupVisibility.PRIVATE);
            response.setMaxMembers(20);
            response.setCreatedBy(creator);
            response.setMembers(java.util.List.of());
            response.setCreatedAt(createdAt);
            response.setUpdatedAt(updatedAt);

            assertEquals(10L, response.getId());
            assertEquals("Group", response.getName());
            assertEquals("Description", response.getDescription());
            assertEquals(GroupVisibility.PRIVATE, response.getVisibility());
            assertEquals(20, response.getMaxMembers());
            assertEquals(creator, response.getCreatedBy());
            assertNotNull(response.getMembers());
            assertEquals(createdAt, response.getCreatedAt());
            assertEquals(updatedAt, response.getUpdatedAt());
            assertNotNull(response.toString());
        }
    }
}

