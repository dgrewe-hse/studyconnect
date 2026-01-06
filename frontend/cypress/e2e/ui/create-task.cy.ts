/**
 * UI Tests for Create Task Form
 * Tests the task creation form UI and validation
 */

describe('Create Task Form UI Tests', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/tasks/create')
  })

  it('should display create task form with all fields', () => {
    cy.get('[data-testid="create-task-view"]').should('be.visible')
    cy.get('[data-testid="create-task-title"]').should('contain', 'Create New Task')
    cy.get('[data-testid="task-form"]').should('be.visible')
  })

  it('should display all form fields', () => {
    // Title field
    cy.get('[data-testid="label-title"]').should('be.visible').and('contain', 'Title')
    cy.get('[data-testid="input-title"]').should('be.visible')
    cy.get('[data-testid="input-title"]').should('have.attr', 'required')
    cy.get('[data-testid="input-title"]').should('have.attr', 'maxlength', '200')
    
    // Description field
    cy.get('[data-testid="label-description"]').should('be.visible').and('contain', 'Description')
    cy.get('[data-testid="input-description"]').should('be.visible')
    
    // Due date field
    cy.get('[data-testid="label-due-date"]').should('be.visible').and('contain', 'Due Date')
    cy.get('[data-testid="input-due-date"]').should('be.visible')
    cy.get('[data-testid="input-due-date"]').should('have.attr', 'type', 'datetime-local')
    
    // Priority field
    cy.get('[data-testid="label-priority"]').should('be.visible').and('contain', 'Priority')
    cy.get('[data-testid="select-priority"]').should('be.visible')
    cy.get('[data-testid="select-priority"]').should('have.attr', 'required')
    
    // Category field
    cy.get('[data-testid="label-category"]').should('be.visible').and('contain', 'Category')
    cy.get('[data-testid="input-category"]').should('be.visible')
  })

  it('should have priority options', () => {
    cy.get('[data-testid="select-priority"]').select('LOW')
    cy.get('[data-testid="select-priority"]').should('have.value', 'LOW')
    
    cy.get('[data-testid="select-priority"]').select('MEDIUM')
    cy.get('[data-testid="select-priority"]').should('have.value', 'MEDIUM')
    
    cy.get('[data-testid="select-priority"]').select('HIGH')
    cy.get('[data-testid="select-priority"]').should('have.value', 'HIGH')
  })

  it('should allow entering task details', () => {
    cy.get('[data-testid="input-title"]')
      .type('Test Task Title')
      .should('have.value', 'Test Task Title')
    
    cy.get('[data-testid="input-description"]')
      .type('This is a test task description')
      .should('have.value', 'This is a test task description')
    
    cy.get('[data-testid="input-category"]')
      .type('Test Category')
      .should('have.value', 'Test Category')
  })

  it('should display form action buttons', () => {
    cy.get('[data-testid="form-actions"]').should('be.visible')
    cy.get('[data-testid="btn-submit"]').should('be.visible').and('contain', 'Create Task')
    cy.get('[data-testid="btn-cancel-form"]').should('be.visible').and('contain', 'Cancel')
  })

  it('should navigate back when clicking cancel', () => {
    cy.get('[data-testid="btn-cancel-form"]').click()
    cy.url().should('include', '/tasks')
    cy.get('[data-testid="tasks-view"]').should('be.visible')
  })

  it('should show validation for required fields', () => {
    // Try to submit without title
    cy.get('[data-testid="btn-submit"]').click()
    
    // HTML5 validation should prevent submission
    cy.get('[data-testid="input-title"]').should('have.attr', 'required')
  })

  it('should be responsive on mobile viewport', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="create-task-view"]').should('be.visible')
    cy.get('[data-testid="task-form"]').should('be.visible')
    cy.get('[data-testid="input-title"]').should('be.visible')
  })
})

