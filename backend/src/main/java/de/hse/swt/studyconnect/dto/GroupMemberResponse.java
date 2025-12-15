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

import de.hse.swt.studyconnect.enums.GroupRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for group member responses.
 *
 * <p>
 * This DTO contains information about a user's membership in a study group,
 * including their role and join timestamp.
 * </p>
 *
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Information about a user's membership in a study group")
public class GroupMemberResponse {
    
    /**
     * Unique identifier for the group membership.
     */
    @Schema(description = "Unique identifier of the group membership", example = "15")
    private Long id;
    
    /**
     * Information about the user who is a member of the group.
     */
    @Schema(description = "User information for the group member")
    private UserSummary user;
    
    /**
     * The role of the user within the group.
     */
    @Schema(description = "Role of the user within the group", example = "ADMIN")
    private GroupRole role;
    
    /**
     * Timestamp when the user joined the group.
     */
    @Schema(description = "Timestamp when the user joined the group", example = "2025-05-01T10:15:30")
    private LocalDateTime joinedAt;
    
    /**
     * Information about the user who invited this member to the group.
     */
    @Schema(description = "Information about the user who invited this member to the group")
    private UserSummary invitedBy;
    
    /**
     * Default constructor.
     */
    public GroupMemberResponse() {}
    
    /**
     * Constructor with member information.
     * 
     * @param id the membership ID
     * @param user the user information
     * @param role the group role
     * @param joinedAt the join timestamp
     * @param invitedBy the inviter information
     */
    public GroupMemberResponse(Long id, UserSummary user, GroupRole role, LocalDateTime joinedAt, UserSummary invitedBy) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.joinedAt = joinedAt;
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
     * Gets the information about the user who is a member of the group.
     * 
     * @return the user information
     */
    public UserSummary getUser() {
        return user;
    }
    
    /**
     * Sets the information about the user who is a member of the group.
     * 
     * @param user the user information
     */
    public void setUser(UserSummary user) {
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
     * Gets the information about the user who invited this member to the group.
     * 
     * @return the inviter information
     */
    public UserSummary getInvitedBy() {
        return invitedBy;
    }
    
    /**
     * Sets the information about the user who invited this member to the group.
     * 
     * @param invitedBy the inviter information
     */
    public void setInvitedBy(UserSummary invitedBy) {
        this.invitedBy = invitedBy;
    }
    
    @Override
    public String toString() {
        return "GroupMemberResponse{" +
                "id=" + id +
                ", user=" + user +
                ", role=" + role +
                ", joinedAt=" + joinedAt +
                ", invitedBy=" + invitedBy +
                '}';
    }
}
