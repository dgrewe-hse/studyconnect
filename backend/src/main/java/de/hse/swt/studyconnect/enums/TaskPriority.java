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
 * Enumeration representing the priority levels for tasks in the StudyConnect application.
 * 
 * Tasks can have three priority levels: Low, Medium, and High. This helps users
 * organize and prioritize their academic tasks effectively.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
public enum TaskPriority {
    
    /**
     * Low priority task - can be completed when time permits.
     */
    LOW,
    
    /**
     * Medium priority task - should be completed in a reasonable timeframe.
     */
    MEDIUM,
    
    /**
     * High priority task - requires immediate attention and should be completed first.
     */
    HIGH
}
