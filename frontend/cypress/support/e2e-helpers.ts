/**
 * E2E Test Helpers
 * Utilities for end-to-end tests that interact with the real backend
 */

/// <reference types="cypress" />

// Backend: context-path=/api, controller @RequestMapping("/api/v1/tasks")
// Full path: /api (context-path) + /api/v1/tasks (controller) = /api/api/v1/tasks
// Base URL includes context-path, so endpoint is /api/v1/tasks
const API_BASE_URL = Cypress.env('API_BASE_URL') || 'http://localhost:8080/api'

/**
 * Set the user ID for API requests
 */
export function setUserId(userId: number) {
  window.localStorage.setItem('userId', userId.toString())
}

/**
 * Clear all test data (tasks, groups, etc.)
 * This should be called before each E2E test to ensure clean state
 */
export function clearTestData(userId: number = 1) {
  // Set user ID
  setUserId(userId)
  
  // Clear localStorage
  cy.clearLocalStorage()
  setUserId(userId)
  
  // Optionally, you could call a backend cleanup endpoint here
  // cy.request('DELETE', `${API_BASE_URL}/test/cleanup?userId=${userId}`)
}

/**
 * Create a task via API (for test setup)
 */
export function createTaskViaAPI(task: {
  title: string
  description?: string
  dueDate?: string
  priority?: 'LOW' | 'MEDIUM' | 'HIGH'
  category?: string
  status?: 'OPEN' | 'IN_PROGRESS' | 'COMPLETED'
}, userId: number = 1) {
  setUserId(userId)
  
  return cy.request({
    method: 'POST',
    url: `${API_BASE_URL}/api/v1/tasks`,
    headers: {
      'Content-Type': 'application/json',
      'X-User-Id': userId.toString()
    },
    body: {
      title: task.title,
      description: task.description || '',
      dueDate: task.dueDate || null,
      priority: task.priority || 'MEDIUM',
      category: task.category || null,
      status: task.status || 'OPEN'
    }
  }).then((response) => {
    expect(response.status).to.eq(201)
    return response.body
  })
}

/**
 * Get tasks via API
 */
export function getTasksViaAPI(userId: number = 1) {
  setUserId(userId)
  
  return cy.request({
    method: 'GET',
    url: `${API_BASE_URL}/api/v1/tasks`,
    headers: {
      'X-User-Id': userId.toString()
    }
  }).then((response) => {
    expect(response.status).to.eq(200)
    return response.body
  })
}

/**
 * Delete a task via API
 */
export function deleteTaskViaAPI(taskId: number, userId: number = 1) {
  setUserId(userId)
  
  return cy.request({
    method: 'DELETE',
    url: `${API_BASE_URL}/api/v1/tasks/${taskId}`,
    headers: {
      'X-User-Id': userId.toString()
    }
  })
}

/**
 * Wait for backend to be available
 */
export function waitForBackend(maxAttempts: number = 10) {
  return cy.request({
    method: 'GET',
    // Health endpoint is at /api/health/ping (context-path + controller mapping)
    url: `${API_BASE_URL}/health/ping`,
    failOnStatusCode: false,
    retryOnStatusCodeFailure: false,
    retryOnNetworkFailure: true
  }).then((response) => {
    if (response.status === 200) {
      return
    }
    // If backend is not ready, wait and retry
    if (maxAttempts > 0) {
      cy.wait(1000)
      return waitForBackend(maxAttempts - 1)
    }
    throw new Error('Backend is not available')
  })
}

/**
 * Login and wait for backend to be ready
 */
export function loginWithBackend(username: string = 'testuser', password: string = 'testpass', userId: number = 1) {
  // First, ensure backend is available
  waitForBackend()
  
  // Set user ID
  setUserId(userId)
  
  // Login via UI
  cy.visit('/login')
  cy.get('[data-testid="input-username"]').type(username)
  cy.get('[data-testid="input-password"]').type(password)
  cy.get('[data-testid="btn-login"]').click()
  
  // Wait for redirect
  cy.url({ timeout: 10000 }).should('not.include', '/login')
  cy.get('[data-testid="main-layout"]', { timeout: 10000 }).should('be.visible')
}

