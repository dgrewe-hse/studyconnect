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

/**
 * Data Transfer Object for group summary information.
 *
 * <p>
 * This DTO contains basic group information that is safe to expose in API responses,
 * including the group name, description, and visibility settings.
 * </p>
 *
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Summary information about a study group")
public class GroupSummary {
    
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
     * Default constructor.
     */
    public GroupSummary() {}
    
    /**
     * Constructor with group information.
     * 
     * @param id the group ID
     * @param name the group name
     * @param description the group description
     * @param visibility the group visibility
     */
    public GroupSummary(Long id, String name, String description, GroupVisibility visibility) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.visibility = visibility;
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
    
    @Override
    public String toString() {
        return "GroupSummary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", visibility=" + visibility +
                '}';
    }
}
