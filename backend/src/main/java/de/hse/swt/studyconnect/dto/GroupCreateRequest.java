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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new study group.
 * 
 * This DTO contains all the necessary information to create a new study group,
 * including the group name, description, visibility settings, and member limits.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Request payload for creating a new study group")
public class GroupCreateRequest {
    
    /**
     * Name of the study group.
     */
    @Schema(description = "Name of the study group", example = "Algorithms Study Group", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Group name is required")
    @Size(max = 100, message = "Group name must not exceed 100 characters")
    private String name;
    
    /**
     * Description of the study group.
     */
    @Schema(description = "Short description of the group's purpose", example = "Weekly sessions to prepare for the algorithms exam")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    /**
     * Visibility setting for the group.
     */
    @Schema(description = "Visibility of the group", example = "PRIVATE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Group visibility is required")
    private GroupVisibility visibility;
    
    /**
     * Maximum number of members allowed in the group.
     */
    @Schema(description = "Maximum number of members allowed in the group (default 20, max 50)", example = "20")
    @Min(value = 2, message = "Maximum members must be at least 2")
    @Max(value = 50, message = "Maximum members must not exceed 50")
    private Integer maxMembers;
    
    /**
     * Default constructor.
     */
    public GroupCreateRequest() {
        this.maxMembers = 20; // Default maximum members
    }
    
    /**
     * Constructor with required fields.
     * 
     * @param name the group name
     * @param description the group description
     * @param visibility the group visibility
     */
    public GroupCreateRequest(String name, String description, GroupVisibility visibility) {
        this();
        this.name = name;
        this.description = description;
        this.visibility = visibility;
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
    
    @Override
    public String toString() {
        return "GroupCreateRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", visibility=" + visibility +
                ", maxMembers=" + maxMembers +
                '}';
    }
}
