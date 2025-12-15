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

package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for task responses.
 * 
 * This DTO contains all the information about a task that should be returned
 * to the client, including all task details and related user/group information.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Task details returned to clients")
public class TaskResponse {
    
    /**
     * Unique identifier for the task.
     */
    @Schema(description = "Unique identifier of the task", example = "1001")
    private Long id;
    
    /**
     * Title of the task.
     */
    @Schema(description = "Title of the task", example = "Prepare math exam")
    private String title;
    
    /**
     * Description or notes for the task.
     */
    @Schema(description = "Detailed description or notes for the task", example = "Review chapters 1-5 and solve practice problems")
    private String description;
    
    /**
     * Due date for the task.
     */
    @Schema(description = "Due date and time of the task in ISO-8601 format", example = "2025-06-30T18:00:00")
    private LocalDateTime dueDate;
    
    /**
     * Priority level of the task.
     */
    @Schema(description = "Priority of the task", example = "HIGH")
    private TaskPriority priority;
    
    /**
     * Current status of the task.
     */
    @Schema(description = "Current status of the task", example = "IN_PROGRESS")
    private TaskStatus status;
    
    /**
     * Category or tag for organizing tasks.
     */
    @Schema(description = "Category label for the task", example = "Mathematics")
    private String category;
    
    /**
     * Information about the user who created the task.
     */
    @Schema(description = "Information about the user who created the task")
    private UserSummary createdBy;
    
    /**
     * Information about the user assigned to the task.
     */
    @Schema(description = "Information about the user assigned to the task (for group tasks)")
    private UserSummary assignedTo;
    
    /**
     * Information about the study group this task belongs to.
     */
    @Schema(description = "Information about the study group this task belongs to (null for personal tasks)")
    private GroupSummary group;
    
    /**
     * Timestamp when the task was created.
     */
    @Schema(description = "Timestamp when the task was created", example = "2025-05-01T10:15:30")
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the task was last updated.
     */
    @Schema(description = "Timestamp when the task was last updated", example = "2025-05-02T09:00:00")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     */
    public TaskResponse() {}
    
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
     * Gets the information about the user who created the task.
     * 
     * @return the creator information
     */
    public UserSummary getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Sets the information about the user who created the task.
     * 
     * @param createdBy the creator information
     */
    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Gets the information about the user assigned to the task.
     * 
     * @return the assigned user information
     */
    public UserSummary getAssignedTo() {
        return assignedTo;
    }
    
    /**
     * Sets the information about the user assigned to the task.
     * 
     * @param assignedTo the assigned user information
     */
    public void setAssignedTo(UserSummary assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    /**
     * Gets the information about the study group this task belongs to.
     * 
     * @return the group information
     */
    public GroupSummary getGroup() {
        return group;
    }
    
    /**
     * Sets the information about the study group this task belongs to.
     * 
     * @param group the group information
     */
    public void setGroup(GroupSummary group) {
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
    
    @Override
    public String toString() {
        return "TaskResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status=" + status +
                ", category='" + category + '\'' +
                ", createdBy=" + createdBy +
                ", assignedTo=" + assignedTo +
                ", group=" + group +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
