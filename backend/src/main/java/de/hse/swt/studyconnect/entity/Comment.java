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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Entity representing a comment on a task in the StudyConnect application.
 * 
 * Comments enable collaboration and communication between users working on tasks.
 * Users can add comments to tasks, reply to existing comments, and edit or delete
 * their own comments (with additional permissions for administrators).
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Entity
@Table(name = "comments")
public class Comment {
    
    /**
     * Unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The task this comment belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull(message = "Task is required")
    private Task task;
    
    /**
     * The user who wrote the comment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;
    
    /**
     * The content of the comment.
     */
    @Column(nullable = false, length = 2000)
    @NotBlank(message = "Comment content is required")
    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    private String content;
    
    /**
     * Timestamp when the comment was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the comment was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor for JPA.
     */
    public Comment() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor for creating a new comment.
     * 
     * @param task the task this comment belongs to
     * @param user the user who wrote the comment
     * @param content the comment content
     */
    public Comment(Task task, User user, String content) {
        this();
        this.task = task;
        this.user = user;
        this.content = content;
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
     * Gets the task this comment belongs to.
     * 
     * @return the task
     */
    public Task getTask() {
        return task;
    }
    
    /**
     * Sets the task this comment belongs to.
     * 
     * @param task the task
     */
    public void setTask(Task task) {
        this.task = task;
    }
    
    /**
     * Gets the user who wrote the comment.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the user who wrote the comment.
     * 
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
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
     * Updates the last update timestamp to the current time.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", task=" + (task != null ? task.getTitle() : null) +
                ", user=" + (user != null ? user.getName() : null) +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
