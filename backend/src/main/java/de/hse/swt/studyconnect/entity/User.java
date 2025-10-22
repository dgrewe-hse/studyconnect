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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a user in the StudyConnect application.
 * 
 * Users are the primary actors in the system and can be students or group administrators.
 * Each user has a unique email address and can create personal tasks, join study groups,
 * and participate in collaborative activities.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Entity
@Table(name = "users")
public class User {
    
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * User's email address. Must be unique and valid.
     */
    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    
    /**
     * Hashed password for authentication.
     */
    @Column(name = "password_hash", nullable = false)
    @NotBlank(message = "Password is required")
    private String passwordHash;
    
    /**
     * User's display name.
     */
    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    /**
     * Timestamp when the user account was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the user account was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Timestamp of the user's last login.
     */
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    /**
     * Personal tasks created by this user.
     */
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> personalTasks = new ArrayList<>();
    
    /**
     * Group memberships for this user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupMember> groupMemberships = new ArrayList<>();
    
    /**
     * Comments made by this user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    
    /**
     * Default constructor for JPA.
     */
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor for creating a new user.
     * 
     * @param email the user's email address
     * @param passwordHash the hashed password
     * @param name the user's display name
     */
    public User(String email, String passwordHash, String name) {
        this();
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
    }
    
    /**
     * Gets the unique identifier of the user.
     * 
     * @return the user ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the user.
     * 
     * @param id the user ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the user's email address.
     * 
     * @return the email address
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the user's email address.
     * 
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Gets the hashed password.
     * 
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }
    
    /**
     * Sets the hashed password.
     * 
     * @param passwordHash the password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    /**
     * Gets the user's display name.
     * 
     * @return the display name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the user's display name.
     * 
     * @param name the display name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets the last login timestamp.
     * 
     * @return the last login timestamp
     */
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    /**
     * Sets the last login timestamp.
     * 
     * @param lastLogin the last login timestamp
     */
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    /**
     * Gets the personal tasks created by this user.
     * 
     * @return the list of personal tasks
     */
    public List<Task> getPersonalTasks() {
        return personalTasks;
    }
    
    /**
     * Sets the personal tasks created by this user.
     * 
     * @param personalTasks the list of personal tasks
     */
    public void setPersonalTasks(List<Task> personalTasks) {
        this.personalTasks = personalTasks;
    }
    
    /**
     * Gets the group memberships for this user.
     * 
     * @return the list of group memberships
     */
    public List<GroupMember> getGroupMemberships() {
        return groupMemberships;
    }
    
    /**
     * Sets the group memberships for this user.
     * 
     * @param groupMemberships the list of group memberships
     */
    public void setGroupMemberships(List<GroupMember> groupMemberships) {
        this.groupMemberships = groupMemberships;
    }
    
    /**
     * Gets the comments made by this user.
     * 
     * @return the list of comments
     */
    public List<Comment> getComments() {
        return comments;
    }
    
    /**
     * Sets the comments made by this user.
     * 
     * @param comments the list of comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
