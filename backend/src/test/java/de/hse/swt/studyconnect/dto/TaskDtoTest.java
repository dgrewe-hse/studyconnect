package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for task related DTOs.
 *
 * <p>
 * These tests verify constructor behavior, getter/setter methods and basic
 * toString contract for {@link TaskCreateRequest}, {@link TaskUpdateRequest},
 * {@link TaskStatusUpdateRequest} and {@link TaskResponse}.
 * </p>
 */
@DisplayName("Task DTO Tests")
class TaskDtoTest {

    @Nested
    @DisplayName("TaskCreateRequest Tests")
    class TaskCreateRequestTests {

        @Test
        @DisplayName("Parameterized constructor should set required fields")
        void parameterizedConstructorShouldSetRequiredFields() {
            LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
            TaskCreateRequest request = new TaskCreateRequest("Title", dueDate, TaskPriority.HIGH);

            assertEquals("Title", request.getTitle());
            assertEquals(dueDate, request.getDueDate());
            assertEquals(TaskPriority.HIGH, request.getPriority());
        }

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWork() {
            TaskCreateRequest request = new TaskCreateRequest();
            LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

            request.setTitle("Task");
            request.setDescription("Description");
            request.setDueDate(dueDate);
            request.setPriority(TaskPriority.MEDIUM);
            request.setCategory("Category");
            request.setGroupId(10L);
            request.setAssignedToId(5L);

            assertEquals("Task", request.getTitle());
            assertEquals("Description", request.getDescription());
            assertEquals(dueDate, request.getDueDate());
            assertEquals(TaskPriority.MEDIUM, request.getPriority());
            assertEquals("Category", request.getCategory());
            assertEquals(10L, request.getGroupId());
            assertEquals(5L, request.getAssignedToId());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("TaskUpdateRequest Tests")
    class TaskUpdateRequestTests {

        @Test
        @DisplayName("Should get and set all mutable properties")
        void shouldGetAndSetAllMutableProperties() {
            TaskUpdateRequest request = new TaskUpdateRequest();
            LocalDateTime dueDate = LocalDateTime.now().plusDays(3);

            request.setTitle("Updated");
            request.setDescription("Updated description");
            request.setDueDate(dueDate);
            request.setPriority(TaskPriority.LOW);
            request.setStatus(TaskStatus.IN_PROGRESS);
            request.setCategory("Updated category");
            request.setAssignedToId(7L);

            assertEquals("Updated", request.getTitle());
            assertEquals("Updated description", request.getDescription());
            assertEquals(dueDate, request.getDueDate());
            assertEquals(TaskPriority.LOW, request.getPriority());
            assertEquals(TaskStatus.IN_PROGRESS, request.getStatus());
            assertEquals("Updated category", request.getCategory());
            assertEquals(7L, request.getAssignedToId());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("TaskStatusUpdateRequest Tests")
    class TaskStatusUpdateRequestTests {

        @Test
        @DisplayName("Convenience constructor should set status")
        void convenienceConstructorShouldSetStatus() {
            TaskStatusUpdateRequest request = new TaskStatusUpdateRequest(TaskStatus.COMPLETED);
            assertEquals(TaskStatus.COMPLETED, request.getStatus());
        }

        @Test
        @DisplayName("Getter and setter should work for status property")
        void getterAndSetterShouldWorkForStatusProperty() {
            TaskStatusUpdateRequest request = new TaskStatusUpdateRequest();
            request.setStatus(TaskStatus.OPEN);

            assertEquals(TaskStatus.OPEN, request.getStatus());
            assertNotNull(request.toString());
        }
    }

    @Nested
    @DisplayName("TaskResponse Tests")
    class TaskResponseTests {

        @Test
        @DisplayName("Getter and setter should work for all properties")
        void gettersAndSettersShouldWorkForAllProperties() {
            TaskResponse response = new TaskResponse();
            LocalDateTime createdAt = LocalDateTime.of(2025, 1, 1, 10, 0);
            LocalDateTime updatedAt = LocalDateTime.of(2025, 1, 2, 11, 0);
            UserSummary creator = new UserSummary(1L, "Creator");
            UserSummary assignee = new UserSummary(2L, "Assignee");
            GroupSummary group = new GroupSummary(3L, "Group", "Description", de.hse.swt.studyconnect.enums.GroupVisibility.PRIVATE);

            response.setId(100L);
            response.setTitle("Title");
            response.setDescription("Description");
            response.setDueDate(LocalDateTime.now().plusDays(1));
            response.setPriority(TaskPriority.HIGH);
            response.setStatus(TaskStatus.OPEN);
            response.setCategory("Category");
            response.setCreatedBy(creator);
            response.setAssignedTo(assignee);
            response.setGroup(group);
            response.setCreatedAt(createdAt);
            response.setUpdatedAt(updatedAt);

            assertEquals(100L, response.getId());
            assertEquals("Title", response.getTitle());
            assertEquals("Description", response.getDescription());
            assertEquals(TaskPriority.HIGH, response.getPriority());
            assertEquals(TaskStatus.OPEN, response.getStatus());
            assertEquals("Category", response.getCategory());
            assertEquals(creator, response.getCreatedBy());
            assertEquals(assignee, response.getAssignedTo());
            assertEquals(group, response.getGroup());
            assertEquals(createdAt, response.getCreatedAt());
            assertEquals(updatedAt, response.getUpdatedAt());
            assertNotNull(response.toString());
        }
    }
}

