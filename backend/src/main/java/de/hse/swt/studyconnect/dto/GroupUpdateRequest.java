package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.GroupVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an existing study group.
 *
 * <p>
 * This DTO is used for updating mutable properties of a group such as name,
 * description, visibility, and member limit.
 * </p>
 */
@Schema(description = "Request payload for updating an existing study group")
public class GroupUpdateRequest {

    /**
     * Name of the study group.
     */
    @Schema(description = "Name of the study group", example = "Algorithms Study Group")
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
    @Schema(description = "Visibility of the group", example = "PRIVATE")
    private GroupVisibility visibility;

    /**
     * Maximum number of members allowed in the group.
     */
    @Schema(description = "Maximum number of members allowed in the group", example = "20")
    @Min(value = 2, message = "Maximum members must be at least 2")
    @Max(value = 50, message = "Maximum members must not exceed 50")
    private Integer maxMembers;

    /**
     * Default constructor.
     */
    public GroupUpdateRequest() {
        // Default constructor for frameworks
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
        return "GroupUpdateRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", visibility=" + visibility +
                ", maxMembers=" + maxMembers +
                '}';
    }
}

