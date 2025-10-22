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

package de.hse.swt.studyconnect.enums;

/**
 * Enumeration representing the roles that users can have within study groups.
 * 
 * The StudyConnect application supports two main roles: Student (default role)
 * and Group Administrator. Each role has different permissions and capabilities
 * within the group context.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
public enum GroupRole {
    
    /**
     * Default role for all users. Students can create personal tasks,
     * join groups, view and comment on group tasks, and export personal data.
     */
    STUDENT,
    
    /**
     * Administrative role with elevated permissions. Group administrators can
     * create and manage study groups, invite and remove members, assign tasks,
     * moderate discussions, and export group data.
     */
    ADMIN
}
