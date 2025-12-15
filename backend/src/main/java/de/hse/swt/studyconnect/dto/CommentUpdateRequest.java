package de.hse.swt.studyconnect.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an existing comment.
 *
 * <p>
 * This DTO is used when a user edits the textual content of a comment.
 * </p>
 */
@Schema(description = "Request payload for updating an existing comment")
public class CommentUpdateRequest {

    /**
     * The updated content of the comment.
     */
    @Schema(description = "Updated comment text", example = "I have finished the first part of the assignment.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Comment content is required")
    @Size(max = 2000, message = "Comment must not exceed 2000 characters")
    private String content;

    /**
     * Default constructor.
     */
    public CommentUpdateRequest() {
        // Default constructor for frameworks
    }

    /**
     * Convenience constructor.
     *
     * @param content updated comment content
     */
    public CommentUpdateRequest(String content) {
        this.content = content;
    }

    /**
     * Gets the updated content of the comment.
     *
     * @return the comment content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the updated content of the comment.
     *
     * @param content the comment content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentUpdateRequest{" +
                "content='" + content + '\'' +
                '}';
    }
}

