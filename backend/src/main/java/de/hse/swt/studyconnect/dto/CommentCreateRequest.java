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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new comment.
 *
 * <p>
 * This DTO contains the necessary information to create a new comment on a task,
 * including the task ID and comment content.
 * </p>
 *
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Request payload for creating a new comment on a task")
public class CommentCreateRequest {
    
    /**
     * ID of the task this comment belongs to.
     */
    @Schema(description = "Identifier of the task the comment belongs to", example = "1001", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Task ID is required")
    private Long taskId;
    
    /**
     * The content of the comment.
     */
    @Schema(description = "Comment text", example = "Let's split the assignment into two parts.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Comment content is required")
    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    private String content;
    
    /**
     * Default constructor.
     */
    public CommentCreateRequest() {}
    
    /**
     * Constructor with required fields.
     * 
     * @param taskId the task ID
     * @param content the comment content
     */
    public CommentCreateRequest(Long taskId, String content) {
        this.taskId = taskId;
        this.content = content;
    }
    
    /**
     * Gets the ID of the task this comment belongs to.
     * 
     * @return the task ID
     */
    public Long getTaskId() {
        return taskId;
    }
    
    /**
     * Sets the ID of the task this comment belongs to.
     * 
     * @param taskId the task ID
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    /**
     * Gets the content of the comment.
     * 
     * @return the comment content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Sets the content of the comment.
     * 
     * @param content the comment content
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "CommentCreateRequest{" +
                "taskId=" + taskId +
                ", content='" + content + '\'' +
                '}';
    }
}
