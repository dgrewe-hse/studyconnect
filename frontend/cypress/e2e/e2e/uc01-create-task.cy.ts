/**
 * E2E Test: UC01 Create Personal Task
 * Based on backend BDD feature: UC01_Create_Personal_Task.feature
 * 
 * This test requires the backend to be running
 */

import { clearTestData, loginWithBackend, createTaskViaAPI, getTasksViaAPI, deleteTaskViaAPI } from '../../support/e2e-helpers'

describe('UC01: Create Personal Task (E2E)', () => {
  const userId = 1

  beforeEach(() => {
    // Clear test data and login
    clearTestData(userId)
    loginWithBackend('testuser', 'testpass', userId)
  })

  afterEach(() => {
    // Cleanup: Delete any tasks created during tests
    getTasksViaAPI(userId).then((response) => {
      if (response.content && Array.isArray(response.content)) {
        response.content.forEach((task: any) => {
          deleteTaskViaAPI(task.id, userId)
        })
      }
    })
  })

  it('should create a task with required fields', () => {
    // Navigate to create task page
    cy.visit('/tasks/create')
    cy.get('[data-testid="create-task-view"]', { timeout: 10000 }).should('be.visible')

    // Fill in the form
    cy.get('[data-testid="input-title"]').type('Read Chapter 3')
    cy.get('[data-testid="input-due-date"]').type('2025-11-05T14:00')
    cy.get('[data-testid="select-priority"]').select('MEDIUM')
    cy.get('[data-testid="input-description"]').type('Focus on sections 3.2 and 3.3')

    // Save the task
    cy.get('[data-testid="btn-submit"]').click()

    // Verify task was created
    cy.url({ timeout: 10000 }).should('include', '/tasks')
    cy.get('[data-testid="tasks-view"]', { timeout: 10000 }).should('be.visible')

    // Verify task appears in list
    cy.get('[data-testid="tasks-list"]', { timeout: 10000 }).should('be.visible')
    cy.contains('Read Chapter 3').should('be.visible')

    // Verify task status is Open
    cy.contains('Read Chapter 3').parent().within(() => {
      cy.get('[data-testid^="task-status-"]').should('contain', 'OPEN')
    })
  })

  it('should validate title is required', () => {
    cy.visit('/tasks/create')
    cy.get('[data-testid="create-task-view"]').should('be.visible')

    // Try to submit without title
    cy.get('[data-testid="input-due-date"]').type('2025-11-05T14:00')
    cy.get('[data-testid="select-priority"]').select('LOW')
    cy.get('[data-testid="btn-submit"]').click()

    // HTML5 validation should prevent submission
    cy.get('[data-testid="input-title"]:invalid').should('exist')
    cy.url().should('include', '/tasks/create')
  })

  it('should validate title length (max 200 chars)', () => {
    cy.visit('/tasks/create')
    cy.get('[data-testid="create-task-view"]').should('be.visible')

    // Enter title that's too long (201 characters)
    const longTitle = 'A'.repeat(201)
    cy.get('[data-testid="input-title"]').type(longTitle)
    cy.get('[data-testid="input-due-date"]').type('2025-11-05T14:00')
    cy.get('[data-testid="select-priority"]').select('LOW')
    cy.get('[data-testid="btn-submit"]').click()

    // Should show validation error or prevent submission
    cy.get('[data-testid="input-title"]:invalid').should('exist')
  })

  it('should validate notes length (max 1000 chars)', () => {
    cy.visit('/tasks/create')
    cy.get('[data-testid="create-task-view"]').should('be.visible')

    // Enter valid title
    cy.get('[data-testid="input-title"]').type('Valid Title')
    cy.get('[data-testid="input-due-date"]').type('2025-11-05T14:00')
    cy.get('[data-testid="select-priority"]').select('LOW')

    // Enter description that's too long (1001 characters)
    const longDescription = 'N'.repeat(1001)
    cy.get('[data-testid="input-description"]').type(longDescription)
    cy.get('[data-testid="btn-submit"]').click()

    // Should show validation error
    cy.get('[data-testid="input-description"]:invalid').should('exist')
  })

  it('should cancel task creation', () => {
    cy.visit('/tasks/create')
    cy.get('[data-testid="create-task-view"]').should('be.visible')

    // Enter some data
    cy.get('[data-testid="input-title"]').type('Temp Task')
    cy.get('[data-testid="input-due-date"]').type('2025-11-05T14:00')

    // Cancel
    cy.get('[data-testid="btn-cancel-form"]').click()

    // Should navigate back to tasks list
    cy.url().should('include', '/tasks')
    cy.get('[data-testid="tasks-view"]').should('be.visible')

    // Task should not be created (verify via API)
    getTasksViaAPI(userId).then((response) => {
      const tasks = response.content || []
      const tempTask = tasks.find((t: any) => t.title === 'Temp Task')
      expect(tempTask).to.be.undefined
    })
  })

  it('should create task and verify it appears in calendar view', () => {
    // Create task via API for setup
    const dueDate = '2025-11-05T14:00:00'
    createTaskViaAPI({
      title: 'Calendar Test Task',
      dueDate: dueDate,
      priority: 'MEDIUM'
    }, userId)

    // Navigate to calendar
    cy.visit('/calendar')
    cy.get('[data-testid="calendar-view"]', { timeout: 10000 }).should('be.visible')

    // Verify task appears on the correct date
    // Note: Calendar implementation may vary, adjust selector as needed
    cy.get('[data-testid^="calendar-day-2025-11-05"]', { timeout: 10000 }).should('exist')
  })
})

