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

import de.hse.swt.studyconnect.enums.GroupVisibility;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for group responses.
 * 
 * This DTO contains all the information about a study group that should be returned
 * to the client, including group details, member information, and timestamps.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Full details of a study group returned to clients")
public class GroupResponse {
    
    /**
     * Unique identifier for the group.
     */
    @Schema(description = "Unique identifier of the group", example = "5")
    private Long id;
    
    /**
     * Name of the study group.
     */
    @Schema(description = "Name of the study group", example = "Algorithms Study Group")
    private String name;
    
    /**
     * Description of the study group.
     */
    @Schema(description = "Short description of the group", example = "Weekly sessions to prepare for the algorithms exam")
    private String description;
    
    /**
     * Visibility setting for the group.
     */
    @Schema(description = "Visibility of the group", example = "PRIVATE")
    private GroupVisibility visibility;
    
    /**
     * Maximum number of members allowed in the group.
     */
    @Schema(description = "Maximum number of members allowed in the group", example = "20")
    private Integer maxMembers;
    
    /**
     * Information about the user who created the group.
     */
    @Schema(description = "Information about the user who created the group")
    private UserSummary createdBy;
    
    /**
     * List of group members.
     */
    @Schema(description = "List of current group members")
    private List<GroupMemberResponse> members;
    
    /**
     * Timestamp when the group was created.
     */
    @Schema(description = "Timestamp when the group was created", example = "2025-05-01T10:15:30")
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the group was last updated.
     */
    @Schema(description = "Timestamp when the group was last updated", example = "2025-05-02T09:00:00")
    private LocalDateTime updatedAt;
    
    /**
     * Default constructor.
     */
    public GroupResponse() {}
    
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
     * Gets the information about the user who created the group.
     * 
     * @return the creator information
     */
    public UserSummary getCreatedBy() {
        return createdBy;
    }
    
    /**
     * Sets the information about the user who created the group.
     * 
     * @param createdBy the creator information
     */
    public void setCreatedBy(UserSummary createdBy) {
        this.createdBy = createdBy;
    }
    
    /**
     * Gets the list of group members.
     * 
     * @return the list of members
     */
    public List<GroupMemberResponse> getMembers() {
        return members;
    }
    
    /**
     * Sets the list of group members.
     * 
     * @param members the list of members
     */
    public void setMembers(List<GroupMemberResponse> members) {
        this.members = members;
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
        return "GroupResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", visibility=" + visibility +
                ", maxMembers=" + maxMembers +
                ", createdBy=" + createdBy +
                ", members=" + members +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
