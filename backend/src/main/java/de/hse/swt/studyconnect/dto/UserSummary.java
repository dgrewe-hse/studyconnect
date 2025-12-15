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

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for user summary information.
 *
 * <p>
 * This DTO contains basic user information that is safe to expose in API responses,
 * excluding sensitive data like passwords or email addresses.
 * </p>
 *
 * @author StudyConnect Team
 * @version 1.0.0
 */
@Schema(description = "Summary information about a user that is safe to expose to clients")
public class UserSummary {
    
    /**
     * Unique identifier for the user.
     */
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;
    
    /**
     * User's display name.
     */
    @Schema(description = "Display name of the user", example = "Alex Student")
    private String name;
    
    /**
     * Default constructor.
     */
    public UserSummary() {}
    
    /**
     * Constructor with user information.
     * 
     * @param id the user ID
     * @param name the user's display name
     */
    public UserSummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * Gets the unique identifier of the user.
     * 
     * @return the user ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the user.
     * 
     * @param id the user ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the user's display name.
     * 
     * @return the display name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the user's display name.
     * 
     * @param name the display name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "UserSummary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
