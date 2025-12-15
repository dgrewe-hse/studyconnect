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

import java.time.LocalDateTime;

/**
 * Data Transfer Object for comment responses.
 *
 * <p>
 * This DTO contains all the information about a comment that should be returned
 * to the client, including the comment content, author information, and timestamps.
 * </p>
 *
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Comment details returned to clients")
public class CommentResponse {
    
    /**
     * Unique identifier for the comment.
     */
    @Schema(description = "Unique identifier of the comment", example = "501")
    private Long id;
    
    /**
     * The content of the comment.
     */
    @Schema(description = "Comment text", example = "Let's split the assignment into two parts.")
    private String content;
    
    /**
     * Information about the user who wrote the comment.
     */
    @Schema(description = "Information about the user who wrote the comment")
    private UserSummary user;
    
    /**
     * Timestamp when the comment was created.
     */
    @Schema(description = "Timestamp when the comment was created", example = "2025-05-01T10:15:30")
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the comment was last updated.
     */
    @Schema(description = "Timestamp when the comment was last updated", example = "2025-05-01T11:00:00")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     */
    public CommentResponse() {}
    
    /**
     * Constructor with comment information.
     * 
     * @param id the comment ID
     * @param content the comment content
     * @param user the user information
     * @param createdAt the creation timestamp
     * @param updatedAt the update timestamp
     */
    public CommentResponse(Long id, String content, UserSummary user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    /**
     * Gets the unique identifier of the comment.
     * 
     * @return the comment ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the comment.
     * 
     * @param id the comment ID
     */
    public void setId(Long id) {
        this.id = id;
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
    
    /**
     * Gets the information about the user who wrote the comment.
     * 
     * @return the user information
     */
    public UserSummary getUser() {
        return user;
    }
    
    /**
     * Sets the information about the user who wrote the comment.
     * 
     * @param user the user information
     */
    public void setUser(UserSummary user) {
        this.user = user;
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
        return "CommentResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
