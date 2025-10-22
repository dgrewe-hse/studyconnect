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
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a task in the StudyConnect application.
 * 
 * Tasks can be either personal tasks (created by individual users) or group tasks
 * (assigned to study group members). Tasks have required fields like title, due date,
 * and priority, as well as optional fields like category, status, and tags.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Entity
@Table(name = "tasks")
public class Task {
    
    /**
     * Unique identifier for the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Title of the task.
     */
    @Column(nullable = false)
    @NotBlank(message = "Task title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    /**
     * Description or notes for the task.
     */
    @Column(length = 1000)
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    /**
     * Due date for the task.
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    
    /**
     * Priority level of the task.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Task priority is required")
    private TaskPriority priority;
    
    /**
     * Current status of the task.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Task status is required")
    private TaskStatus status;
    
    /**
     * Category or tag for organizing tasks.
     */
    @Column(length = 50)
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;
    
    /**
     * User who created the task.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "Task creator is required")
    private User createdBy;
    
    /**
     * User assigned to the task (for group tasks).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
    
    /**
     * Study group this task belongs to (null for personal tasks).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    
    /**
     * Timestamp when the task was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the task was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Comments on this task.
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    
    /**
     * Default constructor for JPA.
     */
    public Task() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = TaskStatus.OPEN;
    }
    
    /**
     * Constructor for creating a new task.
     * 
     * @param title the task title
     * @param description the task description
     * @param dueDate the due date
     * @param priority the task priority
     * @param createdBy the user who created the task
     */
    public Task(String title, String description, LocalDateTime dueDate, TaskPriority priority, User createdBy) {
        this();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.createdBy = createdBy;
    }
    
    /**
     * Gets the unique identifier of the task.
     * 
     * @return the task ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the task.
     * 
     * @param id the task ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the title of the task.
     * 
     * @return the task title
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Sets the title of the task.
     * 
     * @param title the task title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Gets the description of the task.
     * 
     * @return the task description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of the task.
     * 
     * @param description the task description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the due date of the task.
     * 
     * @return the due date
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    /**
     * Sets the due date of the task.
     * 
     * @param dueDate the due date
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    
    /**
     * Gets the priority of the task.
     * 
     * @return the task priority
     */
    public TaskPriority getPriority() {
        return priority;
    }
    
    /**
     * Sets the priority of the task.
     * 
     * @param priority the task priority
     */
    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    
    /**
     * Gets the status of the task.
     * 
     * @return the task status
     */
    public TaskStatus getStatus() {
        return status;
    }
    
    /**
     * Sets the status of the task.
     * 
     * @param status the task status
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    
    /**
     * Gets the category of the task.
     * 
     * @return the task category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Sets the category of the task.
     * 
     * @param category the task category
     */
    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * Gets the user who created the task.
     * 
     * @return the task creator
     */
    public User getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Sets the user who created the task.
     * 
     * @param createdBy the task creator
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Gets the user assigned to the task.
     * 
     * @return the assigned user
     */
    public User getAssignedTo() {
        return assignedTo;
    }
    
    /**
     * Sets the user assigned to the task.
     * 
     * @param assignedTo the assigned user
     */
    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    /**
     * Gets the study group this task belongs to.
     * 
     * @return the study group (null for personal tasks)
     */
    public Group getGroup() {
        return group;
    }
    
    /**
     * Sets the study group this task belongs to.
     * 
     * @param group the study group (null for personal tasks)
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    
    /**
     * Gets the creation timestamp.
     * 
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Sets the creation timestamp.
     * 
     * @param createdAt the creation timestamp
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Gets the last update timestamp.
     * 
     * @return the last update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * Sets the last update timestamp.
     * 
     * @param updatedAt the last update timestamp
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * Gets the comments on this task.
     * 
     * @return the list of comments
     */
    public List<Comment> getComments() {
        return comments;
    }
    
    /**
     * Sets the comments on this task.
     * 
     * @param comments the list of comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    /**
     * Checks if this is a personal task (not assigned to a group).
     * 
     * @return true if this is a personal task, false if it's a group task
     */
    public boolean isPersonalTask() {
        return this.group == null;
    }
    
    /**
     * Checks if this is a group task (assigned to a study group).
     * 
     * @return true if this is a group task, false if it's a personal task
     */
    public boolean isGroupTask() {
        return this.group != null;
    }
    
    /**
     * Checks if the task is overdue.
     * 
     * @return true if the task is overdue, false otherwise
     */
    public boolean isOverdue() {
        return this.dueDate != null && 
               this.dueDate.isBefore(LocalDateTime.now()) && 
               !TaskStatus.COMPLETED.equals(this.status);
    }
    
    /**
     * Updates the last update timestamp to the current time.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status=" + status +
                ", category='" + category + '\'' +
                ", createdBy=" + (createdBy != null ? createdBy.getName() : null) +
                ", assignedTo=" + (assignedTo != null ? assignedTo.getName() : null) +
                ", group=" + (group != null ? group.getName() : null) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
