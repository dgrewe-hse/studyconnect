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

import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
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
 * Unit tests for Task entity.
 * 
 * Tests the Task entity using equivalence class partitioning and boundary value analysis.
 * Covers constructor behavior, getter/setter methods, equality, hash code, and toString.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@DisplayName("Task Entity Tests")
class TaskTest {

    private Task task;
    private User creator;
    private User assignee;
    private Group group;
    private final String VALID_TITLE = "Complete Assignment";
    private final String VALID_DESCRIPTION = "Finish the programming assignment";
    private final LocalDateTime VALID_DUE_DATE = LocalDateTime.now().plusDays(7);
    private final TaskPriority VALID_PRIORITY = TaskPriority.HIGH;
    private final String VALID_CATEGORY = "Programming";

    @BeforeEach
    void setUp() {
        task = new Task();
        creator = new User("creator@example.com", "password", "Creator");
        assignee = new User("assignee@example.com", "password", "Assignee");
        group = new Group("Test Group", "Test Description", de.hse.swt.studyconnect.enums.GroupVisibility.PRIVATE, creator);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should create task with default values")
        void defaultConstructorShouldCreateTaskWithDefaultValues() {
            Task newTask = new Task();
            
            assertNotNull(newTask.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newTask.getUpdatedAt(), "UpdatedAt should be set");
            assertNotNull(newTask.getComments(), "Comments should be initialized");
            assertTrue(newTask.getComments().isEmpty(), "Comments should be empty");
            assertEquals(TaskStatus.OPEN, newTask.getStatus(), "Default status should be OPEN");
        }

        @Test
        @DisplayName("Parameterized constructor should set all fields correctly")
        void parameterizedConstructorShouldSetAllFieldsCorrectly() {
            Task newTask = new Task(VALID_TITLE, VALID_DESCRIPTION, VALID_DUE_DATE, VALID_PRIORITY, creator);
            
            assertEquals(VALID_TITLE, newTask.getTitle(), "Title should be set");
            assertEquals(VALID_DESCRIPTION, newTask.getDescription(), "Description should be set");
            assertEquals(VALID_DUE_DATE, newTask.getDueDate(), "DueDate should be set");
            assertEquals(VALID_PRIORITY, newTask.getPriority(), "Priority should be set");
            assertEquals(creator, newTask.getCreatedBy(), "CreatedBy should be set");
            assertNotNull(newTask.getCreatedAt(), "CreatedAt should be set");
            assertNotNull(newTask.getUpdatedAt(), "UpdatedAt should be set");
        }
    }

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("Should get and set ID correctly")
        void shouldGetAndSetIdCorrectly() {
            Long testId = 1L;
            
            task.setId(testId);
            assertEquals(testId, task.getId(), "ID should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set title correctly")
        void shouldGetAndSetTitleCorrectly() {
            task.setTitle(VALID_TITLE);
            assertEquals(VALID_TITLE, task.getTitle(), "Title should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set description correctly")
        void shouldGetAndSetDescriptionCorrectly() {
            task.setDescription(VALID_DESCRIPTION);
            assertEquals(VALID_DESCRIPTION, task.getDescription(), "Description should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set due date correctly")
        void shouldGetAndSetDueDateCorrectly() {
            task.setDueDate(VALID_DUE_DATE);
            assertEquals(VALID_DUE_DATE, task.getDueDate(), "DueDate should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set priority correctly")
        void shouldGetAndSetPriorityCorrectly() {
            task.setPriority(VALID_PRIORITY);
            assertEquals(VALID_PRIORITY, task.getPriority(), "Priority should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set status correctly")
        void shouldGetAndSetStatusCorrectly() {
            TaskStatus testStatus = TaskStatus.IN_PROGRESS;
            task.setStatus(testStatus);
            assertEquals(testStatus, task.getStatus(), "Status should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set category correctly")
        void shouldGetAndSetCategoryCorrectly() {
            task.setCategory(VALID_CATEGORY);
            assertEquals(VALID_CATEGORY, task.getCategory(), "Category should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set created by correctly")
        void shouldGetAndSetCreatedByCorrectly() {
            task.setCreatedBy(creator);
            assertEquals(creator, task.getCreatedBy(), "CreatedBy should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set assigned to correctly")
        void shouldGetAndSetAssignedToCorrectly() {
            task.setAssignedTo(assignee);
            assertEquals(assignee, task.getAssignedTo(), "AssignedTo should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set group correctly")
        void shouldGetAndSetGroupCorrectly() {
            task.setGroup(group);
            assertEquals(group, task.getGroup(), "Group should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set created at correctly")
        void shouldGetAndSetCreatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            task.setCreatedAt(testTime);
            assertEquals(testTime, task.getCreatedAt(), "CreatedAt should be set and retrieved correctly");
        }

        @Test
        @DisplayName("Should get and set updated at correctly")
        void shouldGetAndSetUpdatedAtCorrectly() {
            LocalDateTime testTime = LocalDateTime.now();
            task.setUpdatedAt(testTime);
            assertEquals(testTime, task.getUpdatedAt(), "UpdatedAt should be set and retrieved correctly");
        }
    }

    @Nested
    @DisplayName("Collection Tests")
    class CollectionTests {

        @Test
        @DisplayName("Should get and set comments correctly")
        void shouldGetAndSetCommentsCorrectly() {
            List<Comment> comments = new ArrayList<>();
            Comment comment1 = new Comment();
            Comment comment2 = new Comment();
            comments.add(comment1);
            comments.add(comment2);
            
            task.setComments(comments);
            assertEquals(comments, task.getComments(), "Comments should be set and retrieved correctly");
            assertEquals(2, task.getComments().size(), "Comments should contain 2 comments");
        }

        @Test
        @DisplayName("Should handle null comments")
        void shouldHandleNullComments() {
            task.setComments(null);
            assertNull(task.getComments(), "Comments should be null");
        }
    }

    @Nested
    @DisplayName("Business Logic Tests")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should correctly identify personal task")
        void shouldCorrectlyIdentifyPersonalTask() {
            task.setGroup(null);
            assertTrue(task.isPersonalTask(), "Should identify as personal task");
            assertFalse(task.isGroupTask(), "Should not identify as group task");
        }

        @Test
        @DisplayName("Should correctly identify group task")
        void shouldCorrectlyIdentifyGroupTask() {
            task.setGroup(group);
            assertTrue(task.isGroupTask(), "Should identify as group task");
            assertFalse(task.isPersonalTask(), "Should not identify as personal task");
        }

        @Test
        @DisplayName("Should correctly identify overdue task")
        void shouldCorrectlyIdentifyOverdueTask() {
            task.setDueDate(LocalDateTime.now().minusDays(1));
            task.setStatus(TaskStatus.OPEN);
            assertTrue(task.isOverdue(), "Should identify as overdue");
        }

        @Test
        @DisplayName("Should not identify completed task as overdue")
        void shouldNotIdentifyCompletedTaskAsOverdue() {
            task.setDueDate(LocalDateTime.now().minusDays(1));
            task.setStatus(TaskStatus.COMPLETED);
            assertFalse(task.isOverdue(), "Should not identify completed task as overdue");
        }

        @Test
        @DisplayName("Should not identify task without due date as overdue")
        void shouldNotIdentifyTaskWithoutDueDateAsOverdue() {
            task.setDueDate(null);
            task.setStatus(TaskStatus.OPEN);
            assertFalse(task.isOverdue(), "Should not identify task without due date as overdue");
        }

        @Test
        @DisplayName("Should not identify future task as overdue")
        void shouldNotIdentifyFutureTaskAsOverdue() {
            task.setDueDate(LocalDateTime.now().plusDays(1));
            task.setStatus(TaskStatus.OPEN);
            assertFalse(task.isOverdue(), "Should not identify future task as overdue");
        }
    }

    @Nested
    @DisplayName("Equality and Hash Code Tests")
    class EqualityTests {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            assertEquals(task, task, "Task should be equal to itself");
            assertEquals(task.hashCode(), task.hashCode(), "Hash code should be consistent");
        }

        @Test
        @DisplayName("Should not be equal to different task instances")
        void shouldNotBeEqualToDifferentTaskInstances() {
            Task task1 = new Task(VALID_TITLE, VALID_DESCRIPTION, VALID_DUE_DATE, VALID_PRIORITY, creator);
            Task task2 = new Task(VALID_TITLE, VALID_DESCRIPTION, VALID_DUE_DATE, VALID_PRIORITY, creator);
            
            // Entities typically don't implement custom equality - they use object identity
            // This test verifies that different instances are not equal even with same values
            assertNotEquals(task1, task2, "Different task instances should not be equal");
            assertNotEquals(task1.hashCode(), task2.hashCode(), "Hash codes should be different");
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            assertNotEquals(task, null, "Task should not be equal to null");
        }

        @Test
        @DisplayName("Should not be equal to different object type")
        void shouldNotBeEqualToDifferentObjectType() {
            String differentObject = "not a task";
            assertNotEquals(task, differentObject, "Task should not be equal to different object type");
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should include all fields in toString")
        void shouldIncludeAllFieldsInToString() {
            task.setId(1L);
            task.setTitle(VALID_TITLE);
            task.setDescription(VALID_DESCRIPTION);
            task.setDueDate(VALID_DUE_DATE);
            task.setPriority(VALID_PRIORITY);
            task.setStatus(TaskStatus.IN_PROGRESS);
            task.setCategory(VALID_CATEGORY);
            task.setCreatedBy(creator);
            task.setAssignedTo(assignee);
            task.setGroup(group);
            task.setCreatedAt(LocalDateTime.of(2023, 1, 1, 12, 0));
            task.setUpdatedAt(LocalDateTime.of(2023, 1, 2, 12, 0));
            
            String toString = task.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("1"), "toString should contain ID");
            assertTrue(toString.contains(VALID_TITLE), "toString should contain title");
            assertTrue(toString.contains(VALID_DESCRIPTION), "toString should contain description");
            assertTrue(toString.contains(VALID_PRIORITY.toString()), "toString should contain priority");
            assertTrue(toString.contains(TaskStatus.IN_PROGRESS.toString()), "toString should contain status");
            assertTrue(toString.contains(VALID_CATEGORY), "toString should contain category");
            assertTrue(toString.contains("Creator"), "toString should contain creator name");
            assertTrue(toString.contains("Assignee"), "toString should contain assignee name");
            assertTrue(toString.contains("Test Group"), "toString should contain group name");
            assertTrue(toString.contains("2023-01-01T12:00"), "toString should contain createdAt");
            assertTrue(toString.contains("2023-01-02T12:00"), "toString should contain updatedAt");
        }

        @Test
        @DisplayName("Should handle null fields in toString")
        void shouldHandleNullFieldsInToString() {
            String toString = task.toString();
            
            assertNotNull(toString, "toString should not be null");
            assertTrue(toString.contains("null"), "toString should contain null for unset fields");
        }
    }

    @Nested
    @DisplayName("Boundary Value Tests")
    class BoundaryValueTests {

        @ParameterizedTest
        @ValueSource(strings = {"A", "Task", "Very Long Task Title That Exceeds Normal Length But Is Still Valid"})
        @DisplayName("Should handle various title lengths")
        void shouldHandleVariousTitleLengths(String title) {
            task.setTitle(title);
            assertEquals(title, task.getTitle(), "Should handle title: " + title);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "Short description", "Very long description that might exceed normal length but is still valid for testing purposes"})
        @DisplayName("Should handle various description lengths")
        void shouldHandleVariousDescriptionLengths(String description) {
            task.setDescription(description);
            assertEquals(description, task.getDescription(), "Should handle description: " + description);
        }

        @ParameterizedTest
        @ValueSource(strings = {"A", "Category", "Very Long Category Name That Exceeds Normal Length But Is Still Valid"})
        @DisplayName("Should handle various category lengths")
        void shouldHandleVariousCategoryLengths(String category) {
            task.setCategory(category);
            assertEquals(category, task.getCategory(), "Should handle category: " + category);
        }

        @ParameterizedTest
        @EnumSource(TaskPriority.class)
        @DisplayName("Should handle all priority types")
        void shouldHandleAllPriorityTypes(TaskPriority priority) {
            task.setPriority(priority);
            assertEquals(priority, task.getPriority(), "Should handle priority: " + priority);
        }

        @ParameterizedTest
        @EnumSource(TaskStatus.class)
        @DisplayName("Should handle all status types")
        void shouldHandleAllStatusTypes(TaskStatus status) {
            task.setStatus(status);
            assertEquals(status, task.getStatus(), "Should handle status: " + status);
        }

        @Test
        @DisplayName("Should handle minimum ID value")
        void shouldHandleMinimumIdValue() {
            Long minId = 1L;
            task.setId(minId);
            assertEquals(minId, task.getId(), "Should handle minimum ID value");
        }

        @Test
        @DisplayName("Should handle maximum ID value")
        void shouldHandleMaximumIdValue() {
            Long maxId = Long.MAX_VALUE;
            task.setId(maxId);
            assertEquals(maxId, task.getId(), "Should handle maximum ID value");
        }
    }

    @Nested
    @DisplayName("Null Value Tests")
    class NullValueTests {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty title")
        void shouldHandleNullAndEmptyTitle(String title) {
            task.setTitle(title);
            assertEquals(title, task.getTitle(), "Should handle null/empty title");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty description")
        void shouldHandleNullAndEmptyDescription(String description) {
            task.setDescription(description);
            assertEquals(description, task.getDescription(), "Should handle null/empty description");
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("Should handle null and empty category")
        void shouldHandleNullAndEmptyCategory(String category) {
            task.setCategory(category);
            assertEquals(category, task.getCategory(), "Should handle null/empty category");
        }

        @Test
        @DisplayName("Should handle null ID")
        void shouldHandleNullId() {
            task.setId(null);
            assertNull(task.getId(), "Should handle null ID");
        }

        @Test
        @DisplayName("Should handle null due date")
        void shouldHandleNullDueDate() {
            task.setDueDate(null);
            assertNull(task.getDueDate(), "Should handle null due date");
        }

        @Test
        @DisplayName("Should handle null priority")
        void shouldHandleNullPriority() {
            task.setPriority(null);
            assertNull(task.getPriority(), "Should handle null priority");
        }

        @Test
        @DisplayName("Should handle null status")
        void shouldHandleNullStatus() {
            task.setStatus(null);
            assertNull(task.getStatus(), "Should handle null status");
        }

        @Test
        @DisplayName("Should handle null created by")
        void shouldHandleNullCreatedBy() {
            task.setCreatedBy(null);
            assertNull(task.getCreatedBy(), "Should handle null createdBy");
        }

        @Test
        @DisplayName("Should handle null assigned to")
        void shouldHandleNullAssignedTo() {
            task.setAssignedTo(null);
            assertNull(task.getAssignedTo(), "Should handle null assignedTo");
        }

        @Test
        @DisplayName("Should handle null group")
        void shouldHandleNullGroup() {
            task.setGroup(null);
            assertNull(task.getGroup(), "Should handle null group");
        }

        @Test
        @DisplayName("Should handle null timestamps")
        void shouldHandleNullTimestamps() {
            task.setCreatedAt(null);
            task.setUpdatedAt(null);
            
            assertNull(task.getCreatedAt(), "Should handle null createdAt");
            assertNull(task.getUpdatedAt(), "Should handle null updatedAt");
        }
    }
}
