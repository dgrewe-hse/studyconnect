package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for updating only the status of an existing task.
 *
 * <p>
 * This DTO is intended for lightweight PATCH operations where only the
 * {@link TaskStatus} value of a task is changed (e.g., mark as completed).
 * </p>
 */
@Schema(description = "Request payload for updating only the status of a task")
public class TaskStatusUpdateRequest {

    /**
     * New status of the task.
     */
    @Schema(description = "New status of the task", example = "COMPLETED", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Task status is required")
    private TaskStatus status;

    /**
     * Default constructor.
     */
    public TaskStatusUpdateRequest() {
        // Default constructor for frameworks
    }

    /**
     * Convenience constructor.
     *
     * @param status new task status
     */
    public TaskStatusUpdateRequest(TaskStatus status) {
        this.status = status;
    }

    /**
     * Gets the new status of the task.
     *
     * @return the task status
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Sets the new status of the task.
     *
     * @param status the task status
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TaskStatusUpdateRequest{" +
                "status=" + status +
                '}';
    }
}

