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

package de.hse.swt.studyconnect.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HealthController.
 * 
 * Tests the health check endpoints to ensure they return
 * the expected responses and status codes.
 * 
 * @author StudyConnect Team
 * @version 1.0.0
 */
class HealthControllerTest {

    private final HealthController healthController = new HealthController();

    /**
     * Test the health endpoint returns correct status and data.
     */
    @Test
    void healthEndpointReturnsCorrectResponse() {
        ResponseEntity<Map<String, Object>> response = healthController.health();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("UP", body.get("status"));
        assertEquals("StudyConnect Backend", body.get("service"));
        assertEquals("1.0.0", body.get("version"));
        assertNotNull(body.get("timestamp"));
    }

    /**
     * Test the ping endpoint returns correct response.
     */
    @Test
    void pingEndpointReturnsCorrectResponse() {
        ResponseEntity<Map<String, String>> response = healthController.ping();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals("pong", body.get("message"));
        assertNotNull(body.get("timestamp"));
    }
}
