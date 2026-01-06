/**
 * UI Tests for Dashboard Page
 * Tests the dashboard layout, statistics, and quick actions
 */

describe('Dashboard UI Tests', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/')
    // Wait for dashboard to be ready (this should always appear regardless of API state)
    cy.get('[data-testid="dashboard"]', { timeout: 10000 }).should('be.visible')
    // Wait for API calls to complete (will fail, but that's expected for UI tests)
    cy.wait(1000) // Give time for API calls to fail
  })

  it('should display dashboard with all sections', () => {
    cy.get('[data-testid="dashboard"]').should('be.visible')
    cy.get('[data-testid="dashboard-title"]').should('contain', 'Dashboard')
    
    // Check priority section
    cy.get('[data-testid="priority-section"]').should('be.visible')
    cy.get('[data-testid="priority-title"]').should('contain', 'Tasks by Priority')
    
    // Check quick actions
    cy.get('[data-testid="quick-actions"]').should('be.visible')
    
    // Check recent tasks section
    cy.get('[data-testid="recent-tasks-section"]').should('be.visible')
  })

  it('should display tasks by priority section', () => {
    cy.get('[data-testid="priority-high"]').should('be.visible')
    cy.get('[data-testid="priority-high"]').should('contain', 'High Priority')
    
    cy.get('[data-testid="priority-medium"]').should('be.visible')
    cy.get('[data-testid="priority-medium"]').should('contain', 'Medium Priority')
    
    cy.get('[data-testid="priority-low"]').should('be.visible')
    cy.get('[data-testid="priority-low"]').should('contain', 'Low Priority')
  })

  it('should display priority counts', () => {
    cy.get('[data-testid="priority-high"]').within(() => {
      cy.get('.priority-count').should('exist')
    })
    
    cy.get('[data-testid="priority-medium"]').within(() => {
      cy.get('.priority-count').should('exist')
    })
    
    cy.get('[data-testid="priority-low"]').within(() => {
      cy.get('.priority-count').should('exist')
    })
  })

  it('should display quick action buttons', () => {
    cy.get('[data-testid="btn-create-task"]')
      .should('be.visible')
      .and('contain', 'Create New Task')
    
    cy.get('[data-testid="btn-create-group"]')
      .should('be.visible')
      .and('contain', 'Create Study Group')
  })

  it('should navigate to create task page when clicking create task button', () => {
    cy.get('[data-testid="btn-create-task"]').click()
    cy.url().should('include', '/tasks/create')
    cy.get('[data-testid="create-task-view"]').should('be.visible')
  })

  it('should navigate to create group page when clicking create group button', () => {
    cy.get('[data-testid="btn-create-group"]').click()
    cy.url().should('include', '/groups/create')
    cy.get('[data-testid="create-group-view"]').should('be.visible')
  })

  it('should display recent tasks section', () => {
    cy.get('[data-testid="recent-tasks-section"]').should('be.visible')
    cy.get('[data-testid="recent-tasks-title"]').should('contain', 'Recent Tasks')
    cy.get('[data-testid="view-all-tasks"]').should('be.visible')
  })

  it('should navigate to tasks page when clicking view all link', () => {
    cy.get('[data-testid="view-all-tasks"]').click()
    cy.url().should('include', '/tasks')
    cy.get('[data-testid="tasks-view"]').should('be.visible')
  })

  it('should handle empty state when no tasks exist', () => {
    // This would require clearing tasks or mocking empty state
    // For now, we check the structure exists
    cy.get('[data-testid="recent-tasks-section"]').should('be.visible')
  })

  it('should be responsive on mobile viewport', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="dashboard"]').should('be.visible')
    cy.get('[data-testid="priority-section"]').should('be.visible')
    cy.get('[data-testid="quick-actions"]').should('be.visible')
  })
})

