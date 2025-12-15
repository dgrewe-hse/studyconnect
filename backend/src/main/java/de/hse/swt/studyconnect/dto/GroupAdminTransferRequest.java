package de.hse.swt.studyconnect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for transferring the administrator role within a group.
 *
 * <p>
 * This DTO is used when the current group administrator wants to transfer
 * their admin privileges to another member of the same group.
 * </p>
 */
@Schema(description = "Request payload for transferring the administrator role within a group")
public class GroupAdminTransferRequest {

    /**
     * ID of the user who should become the new administrator.
     */
    @Schema(description = "User ID of the new group administrator", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "New administrator user ID is required")
    private Long newAdminUserId;

    /**
     * Default constructor.
     */
    public GroupAdminTransferRequest() {
        // Default constructor for frameworks
    }

    /**
     * Convenience constructor.
     *
     * @param newAdminUserId ID of the user who should become the new administrator
     */
    public GroupAdminTransferRequest(Long newAdminUserId) {
        this.newAdminUserId = newAdminUserId;
    }

    /**
     * Gets the ID of the user who should become the new administrator.
     *
     * @return the user ID
     */
    public Long getNewAdminUserId() {
        return newAdminUserId;
    }

    /**
     * Sets the ID of the user who should become the new administrator.
     *
     * @param newAdminUserId the user ID
     */
    public void setNewAdminUserId(Long newAdminUserId) {
        this.newAdminUserId = newAdminUserId;
    }

    @Override
    public String toString() {
        return "GroupAdminTransferRequest{" +
                "newAdminUserId=" + newAdminUserId +
                '}';
    }
}

