package de.hse.swt.studyconnect.dto;

import de.hse.swt.studyconnect.enums.GroupRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for adding a member to a study group.
 *
 * <p>
 * This DTO is used when a group administrator invites or directly adds a user
 * to a study group.
 * </p>
 */
@Schema(description = "Request payload for adding a member to a study group")
public class GroupMemberAddRequest {

    /**
     * ID of the user to add to the group.
     */
    @Schema(description = "ID of the user to add to the group", example = "42", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "User ID is required")
    private Long userId;

    /**
     * Role the user should have within the group.
     * If not provided, the default role MEMBER will be used.
     */
    @Schema(description = "Role of the user within the group. Defaults to MEMBER if omitted", example = "MEMBER")
    private GroupRole role;

    /**
     * Default constructor.
     */
    public GroupMemberAddRequest() {
        // Default constructor for frameworks
    }

    /**
     * Convenience constructor.
     *
     * @param userId the ID of the user to add
     * @param role   the desired group role
     */
    public GroupMemberAddRequest(Long userId, GroupRole role) {
        this.userId = userId;
        this.role = role;
    }

    /**
     * Gets the ID of the user to add.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user to add.
     *
     * @param userId the user ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the desired role for the user.
     *
     * @return the group role
     */
    public GroupRole getRole() {
        return role;
    }

    /**
     * Sets the desired role for the user.
     *
     * @param role the group role
     */
    public void setRole(GroupRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "GroupMemberAddRequest{" +
                "userId=" + userId +
                ", role=" + role +
                '}';
    }
}

