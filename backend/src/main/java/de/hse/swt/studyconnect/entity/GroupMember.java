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

import de.hse.swt.studyconnect.enums.GroupRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Entity representing the relationship between a user and a study group.
 * 
 * This entity manages the membership of users in study groups, including their role
 * within the group (Student or Administrator) and the timestamp when they joined.
 * It also tracks who invited the member to the group.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Entity
@Table(name = "group_members")
public class GroupMember {
    
    /**
     * Unique identifier for the group membership.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The study group this membership belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull(message = "Group is required")
    private Group group;
    
    /**
     * The user who is a member of the group.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required")
    private User user;
    
    /**
     * The role of the user within the group.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Group role is required")
    private GroupRole role;
    
    /**
     * Timestamp when the user joined the group.
     */
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
    
    /**
     * The user who invited this member to the group.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by")
    private User invitedBy;
    
    /**
     * Default constructor for JPA.
     */
    public GroupMember() {
        this.joinedAt = LocalDateTime.now();
    }
    
    /**
     * Constructor for creating a new group membership.
     * 
     * @param group the study group
     * @param user the user joining the group
     * @param role the role of the user in the group
     * @param invitedBy the user who invited this member (can be null for self-joining)
     */
    public GroupMember(Group group, User user, GroupRole role, User invitedBy) {
        this();
        this.group = group;
        this.user = user;
        this.role = role;
        this.invitedBy = invitedBy;
    }
    
    /**
     * Gets the unique identifier of the group membership.
     * 
     * @return the membership ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the group membership.
     * 
     * @param id the membership ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the study group this membership belongs to.
     * 
     * @return the study group
     */
    public Group getGroup() {
        return group;
    }
    
    /**
     * Sets the study group this membership belongs to.
     * 
     * @param group the study group
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    
    /**
     * Gets the user who is a member of the group.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the user who is a member of the group.
     * 
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /**
     * Gets the role of the user within the group.
     * 
     * @return the group role
     */
    public GroupRole getRole() {
        return role;
    }
    
    /**
     * Sets the role of the user within the group.
     * 
     * @param role the group role
     */
    public void setRole(GroupRole role) {
        this.role = role;
    }
    
    /**
     * Gets the timestamp when the user joined the group.
     * 
     * @return the join timestamp
     */
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
    
    /**
     * Sets the timestamp when the user joined the group.
     * 
     * @param joinedAt the join timestamp
     */
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
    
    /**
     * Gets the user who invited this member to the group.
     * 
     * @return the user who sent the invitation
     */
    public User getInvitedBy() {
        return invitedBy;
    }
    
    /**
     * Sets the user who invited this member to the group.
     * 
     * @param invitedBy the user who sent the invitation
     */
    public void setInvitedBy(User invitedBy) {
        this.invitedBy = invitedBy;
    }
    
    /**
     * Checks if this member is an administrator of the group.
     * 
     * @return true if the member is an administrator, false otherwise
     */
    public boolean isAdministrator() {
        return GroupRole.ADMIN.equals(this.role);
    }
    
    /**
     * Checks if this member is a student in the group.
     * 
     * @return true if the member is a student, false otherwise
     */
    public boolean isStudent() {
        return GroupRole.STUDENT.equals(this.role);
    }
    
    @Override
    public String toString() {
        return "GroupMember{" +
                "id=" + id +
                ", group=" + (group != null ? group.getName() : null) +
                ", user=" + (user != null ? user.getName() : null) +
                ", role=" + role +
                ", joinedAt=" + joinedAt +
                ", invitedBy=" + (invitedBy != null ? invitedBy.getName() : null) +
                '}';
    }
}
