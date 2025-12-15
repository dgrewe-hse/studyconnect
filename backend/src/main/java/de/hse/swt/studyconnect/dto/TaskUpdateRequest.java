package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.TaskPriority;
import de.hse.swt.studyconnect.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for updating an existing task.
 *
 * <p>
 * This DTO is used for full updates of a task via HTTP PUT operations.
 * All mutable properties of a task are represented here. Validation rules
 * mirror the creation DTO where applicable.
 * </p>
 */
@Schema(description = "Request payload for updating an existing task")
public class TaskUpdateRequest {

    /**
     * Title of the task.
     */
    @Schema(description = "Title of the task", example = "Prepare math exam")
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
    @Size(max = 50, message = "Category must not exceed 50 characters")
    private String category;

    /**
     * ID of the user assigned to the task (for group tasks).
     */
    @Schema(description = "ID of the user assigned to the task (for group tasks)", example = "42")
    private Long assignedToId;

    /**
     * Default constructor.
     */
    public TaskUpdateRequest() {
        // Default constructor for frameworks
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
        return "TaskUpdateRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status=" + status +
                ", category='" + category + '\'' +
                ", assignedToId=" + assignedToId +
                '}';
    }
}

