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

import de.hse.swt.studyconnect.enums.GroupVisibility;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a study group in the StudyConnect application.
 * 
 * Study groups enable collaborative learning and task management among students.
 * Groups can be private (invitation-only) or public (discoverable), and have
 * configurable settings for member management and task assignment.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Entity
@Table(name = "groups")
public class Group {
    
    /**
     * Unique identifier for the group.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Name of the study group.
     */
    @Column(nullable = false)
    @NotBlank(message = "Group name is required")
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    private String name;
    
    /**
     * Description of the study group.
     */
    @Column(length = 500)
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    /**
     * Visibility setting for the group.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Group visibility is required")
    private GroupVisibility visibility;
    
    /**
     * Maximum number of members allowed in the group.
     */
    @Column(name = "max_members", nullable = false)
    @NotNull(message = "Maximum members is required")
    private Integer maxMembers;
    
    /**
     * User who created the group (becomes the initial administrator).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "Group creator is required")
    private User createdBy;
    
    /**
     * Timestamp when the group was created.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the group was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Members of this group.
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupMember> members = new ArrayList<>();
    
    /**
     * Tasks assigned to this group.
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> groupTasks = new ArrayList<>();
    
    /**
     * Default constructor for JPA.
     */
    public Group() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.maxMembers = 20; // Default maximum members
    }
    
    /**
     * Constructor for creating a new group.
     * 
     * @param name the group name
     * @param description the group description
     * @param visibility the group visibility setting
     * @param createdBy the user who created the group
     */
    public Group(String name, String description, GroupVisibility visibility, User createdBy) {
        this();
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.createdBy = createdBy;
    }
    
    /**
     * Gets the unique identifier of the group.
     * 
     * @return the group ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the group.
     * 
     * @param id the group ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the name of the group.
     * 
     * @return the group name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the group.
     * 
     * @param name the group name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the description of the group.
     * 
     * @return the group description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets the description of the group.
     * 
     * @param description the group description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Gets the visibility setting of the group.
     * 
     * @return the group visibility
     */
    public GroupVisibility getVisibility() {
        return visibility;
    }
    
    /**
     * Sets the visibility setting of the group.
     * 
     * @param visibility the group visibility
     */
    public void setVisibility(GroupVisibility visibility) {
        this.visibility = visibility;
    }
    
    /**
     * Gets the maximum number of members allowed in the group.
     * 
     * @return the maximum number of members
     */
    public Integer getMaxMembers() {
        return maxMembers;
    }
    
    /**
     * Sets the maximum number of members allowed in the group.
     * 
     * @param maxMembers the maximum number of members
     */
    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }
    
    /**
     * Gets the user who created the group.
     * 
     * @return the group creator
     */
    public User getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Sets the user who created the group.
     * 
     * @param createdBy the group creator
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
     * Gets the members of this group.
     * 
     * @return the list of group members
     */
    public List<GroupMember> getMembers() {
        return members;
    }
    
    /**
     * Sets the members of this group.
     * 
     * @param members the list of group members
     */
    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }
    
    /**
     * Gets the tasks assigned to this group.
     * 
     * @return the list of group tasks
     */
    public List<Task> getGroupTasks() {
        return groupTasks;
    }
    
    /**
     * Sets the tasks assigned to this group.
     * 
     * @param groupTasks the list of group tasks
     */
    public void setGroupTasks(List<Task> groupTasks) {
        this.groupTasks = groupTasks;
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
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", visibility=" + visibility +
                ", maxMembers=" + maxMembers +
                ", createdBy=" + (createdBy != null ? createdBy.getName() : null) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
