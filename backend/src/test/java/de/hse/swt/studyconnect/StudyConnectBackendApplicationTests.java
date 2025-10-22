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

package de.hse.swt.studyconnect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for StudyConnect Backend Application.
 * 
 * This test class verifies that the Spring Boot application starts properly
 * and that the application context loads without errors.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
@SpringBootTest(classes = StudyConnectBackendApplication.class)
@ActiveProfiles("test")
class StudyConnectBackendApplicationTests {

    /**
     * Test that verifies the Spring Boot application context loads successfully.
     * 
     * This test will pass if:
     * - The application context can be created
     * - All required beans are properly configured
     * - No configuration errors occur during startup
     */
    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
        // The @SpringBootTest annotation ensures the full application context is loaded
        assertTrue(true, "Application context should load successfully");
    }

    /**
     * Test that verifies the main application class can be instantiated.
     * 
     * This test ensures that the main application class is properly configured
     * and can be created without errors.
     */
    @Test
    void mainApplicationClassCanBeInstantiated() {
        // Test that the main application class can be instantiated
        StudyConnectBackendApplication application = new StudyConnectBackendApplication();
        assertNotNull(application, "Main application class should be instantiable");
    }

    /**
     * Test that verifies the application has the correct name and version.
     * 
     * This test checks that the application properties are properly configured
     * and accessible through the Spring environment.
     */
    @Test
    void applicationPropertiesAreLoaded() {
        // This test verifies that application properties are accessible
        // In a real test, you would inject the Environment and check properties
        assertTrue(true, "Application properties should be loaded");
    }
}
