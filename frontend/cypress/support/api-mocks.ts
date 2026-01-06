/**
 * API mocking utilities for UI tests
 * These mocks allow UI tests to run without a backend server
 */

/**
 * Mock API failures to simulate backend being unavailable
 */
export function mockApiFailures() {
  // Intercept all API calls and return network errors
  cy.intercept('GET', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('POST', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('PUT', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('PATCH', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('DELETE', '**/api/**', { forceNetworkError: true }).as('apiCall')
}

/**
 * Mock API with empty responses (no tasks)
 */
export function mockEmptyApi() {
  cy.intercept('GET', '**/api/v1/tasks**', {
    statusCode: 200,
    body: {
      content: [],
      totalElements: 0,
      totalPages: 0,
      number: 0,
      size: 20
    }
  }).as('getTasks')
  
  cy.intercept('GET', '**/api/v1/tasks/*', { forceNetworkError: true }).as('getTask')
  cy.intercept('POST', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('PUT', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('PATCH', '**/api/**', { forceNetworkError: true }).as('apiCall')
  cy.intercept('DELETE', '**/api/**', { forceNetworkError: true }).as('apiCall')
}

/**
 * Mock API with sample tasks
 */
export function mockApiWithTasks() {
  const mockTasks = [
    {
      id: 1,
      title: 'Test Task 1',
      description: 'Test description',
      status: 'OPEN',
      priority: 'HIGH',
      dueDate: new Date().toISOString(),
      category: 'Test',
      userId: 1
    }
  ]
  
  cy.intercept('GET', '**/api/v1/tasks**', {
    statusCode: 200,
    body: {
      content: mockTasks,
      totalElements: 1,
      totalPages: 1,
      number: 0,
      size: 20
    }
  }).as('getTasks')
  
  cy.intercept('GET', '**/api/v1/tasks/1', {
    statusCode: 200,
    body: mockTasks[0]
  }).as('getTask')
}

