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
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating a new task.
 * 
 * This DTO contains all the necessary information to create a new task,
 * including required fields like title, due date, and priority, as well as
 * optional fields like description and category.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Request payload for creating a new task")
public class TaskCreateRequest {
    
    /**
     * Title of the task.
     */
    @Schema(description = "Title of the task", example = "Prepare math exam", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Task title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    /**
     * Description or notes for the task.
     */
    @Schema(description = "Detailed description or notes for the task", example = "Review chapters 1-5 and solve practice problems")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    /**
     * Due date for the task.
     */
    @Schema(description = "Due date and time of the task in ISO-8601 format", example = "2025-06-30T18:00:00")
    private LocalDateTime dueDate;
    
    /**
     * Priority level of the task.
     */
    @Schema(description = "Priority of the task", example = "HIGH", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Task priority is required")
    private TaskPriority priority;
    
    /**
     * Category or tag for organizing tasks.
     */
    @Schema(description = "Category label for the task", example = "Mathematics")
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;
    
    /**
     * ID of the study group this task belongs to (null for personal tasks).
     */
    @Schema(description = "Identifier of the study group this task belongs to (null for personal tasks)", example = "10")
    private Long groupId;
    
    /**
     * ID of the user assigned to the task (for group tasks).
     */
    @Schema(description = "Identifier of the user assigned to the task (for group tasks)", example = "42")
    private Long assignedToId;
    
    /**
     * Default constructor.
     */
    public TaskCreateRequest() {}
    
    /**
     * Constructor with required fields.
     * 
     * @param title the task title
     * @param dueDate the due date
     * @param priority the task priority
     */
    public TaskCreateRequest(String title, LocalDateTime dueDate, TaskPriority priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
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
     * Gets the ID of the study group this task belongs to.
     * 
     * @return the group ID (null for personal tasks)
     */
    public Long getGroupId() {
        return groupId;
    }
    
    /**
     * Sets the ID of the study group this task belongs to.
     * 
     * @param groupId the group ID (null for personal tasks)
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
    /**
     * Gets the ID of the user assigned to the task.
     * 
     * @return the assigned user ID
     */
    public Long getAssignedToId() {
        return assignedToId;
    }
    
    /**
     * Sets the ID of the user assigned to the task.
     * 
     * @param assignedToId the assigned user ID
     */
    public void setAssignedToId(Long assignedToId) {
        this.assignedToId = assignedToId;
    }
    
    @Override
    public String toString() {
        return "TaskCreateRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", category='" + category + '\'' +
                ", groupId=" + groupId +
                ", assignedToId=" + assignedToId +
                '}';
    }
}
