package de.hse.swt.studyconnect.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for comment and user related DTOs.
 */
@DisplayName("Comment and User DTO Tests")
class CommentAndUserDtoTest {

    @Nested
    @DisplayName("UserSummary Tests")
    class UserSummaryTests {

        @Test
        @DisplayName("Constructor should set all fields")
        void constructorShouldSetAllFields() {
            UserSummary summary = new UserSummary(1L, "User");

            assertEquals(1L, summary.getId());
            assertEquals("User", summary.getName());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            UserSummary summary = new UserSummary();

            summary.setId(2L);
            summary.setName("Another User");

            assertEquals(2L, summary.getId());
            assertEquals("Another User", summary.getName());
            assertNotNull(summary.toString());
        }
    }

    @Nested
    @DisplayName("CommentCreateRequest Tests")
    class CommentCreateRequestTests {

        @Test
        @DisplayName("Constructor should set all fields")
        void constructorShouldSetAllFields() {
            CommentCreateRequest request = new CommentCreateRequest(10L, "Content");

            assertEquals(10L, request.getTaskId());
            assertEquals("Content", request.getContent());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            CommentCreateRequest request = new CommentCreateRequest();

            request.setTaskId(11L);
            request.setContent("Another content");

            assertEquals(11L, request.getTaskId());
            assertEquals("Another content", request.getContent());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("CommentUpdateRequest Tests")
    class CommentUpdateRequestTests {

        @Test
        @DisplayName("Constructor should set content field")
        void constructorShouldSetContentField() {
            CommentUpdateRequest request = new CommentUpdateRequest("Updated content");
            assertEquals("Updated content", request.getContent());
        }

        @Test
        @DisplayName("Getter and setter should work for content")
        void getterAndSetterShouldWorkForContent() {
            CommentUpdateRequest request = new CommentUpdateRequest();
            request.setContent("New content");

            assertEquals("New content", request.getContent());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("CommentResponse Tests")
    class CommentResponseTests {

        @Test
        @DisplayName("Constructor should set all fields")
        void constructorShouldSetAllFields() {
            UserSummary user = new UserSummary(1L, "User");
            LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 10, 0);
            LocalDateTime updatedAt = LocalDateTime.of(2025, 1, 1, 11, 0);
            CommentResponse response = new CommentResponse(5L, "Content", user, createdAt, updatedAt);

            assertEquals(5L, response.getId());
            assertEquals("Content", response.getContent());
            assertEquals(user, response.getUser());
            assertEquals(createdAt, response.getCreatedAt());
            assertEquals(updatedAt, response.getUpdatedAt());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            CommentResponse response = new CommentResponse();
            UserSummary user = new UserSummary(2L, "Another User");
            LocalDateTime createdAt = LocalDateTime.of(2025, 2, 2, 12, 0);
            LocalDateTime updatedAt = LocalDateTime.of(2025, 2, 2, 13, 0);

            response.setId(6L);
            response.setContent("Other content");
            response.setUser(user);
            response.setCreatedAt(createdAt);
            response.setUpdatedAt(updatedAt);

            assertEquals(6L, response.getId());
            assertEquals("Other content", response.getContent());
            assertEquals(user, response.getUser());
            assertEquals(createdAt, response.getCreatedAt());
            assertEquals(updatedAt, response.getUpdatedAt());
            assertNotNull(response.toString());
        }
    }
}

