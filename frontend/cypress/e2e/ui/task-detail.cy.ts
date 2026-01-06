/**
 * UI Tests for Task Detail View
 * Tests the task detail page display and actions
 */

describe('Task Detail View UI Tests', () => {
  beforeEach(() => {
    cy.login()
  })

  it('should display task detail view when navigating to task', () => {
    // First, go to tasks page
    cy.visit('/tasks')
    
    // If tasks exist, click on one
    cy.get('body').then(($body) => {
      if ($body.find('[data-testid^="task-card-"]').length > 0) {
        cy.get('[data-testid^="task-card-"]').first().click()
        
        cy.get('[data-testid="task-detail-view"]').should('be.visible')
        cy.get('[data-testid="task-detail-title"]').should('exist')
      }
    })
  })

  it('should display task badges', () => {
    cy.visit('/tasks')
    
    cy.get('body').then(($body) => {
      if ($body.find('[data-testid^="task-card-"]').length > 0) {
        cy.get('[data-testid^="task-card-"]').first().click()
        
        cy.get('[data-testid="task-badges"]').should('be.visible')
        cy.get('[data-testid="priority-badge"]').should('exist')
        cy.get('[data-testid="status-badge"]').should('exist')
      }
    })
  })

  it('should display action buttons', () => {
    cy.visit('/tasks')
    
    cy.get('body').then(($body) => {
      if ($body.find('[data-testid^="task-card-"]').length > 0) {
        cy.get('[data-testid^="task-card-"]').first().click()
        
        cy.get('[data-testid="btn-edit"]').should('be.visible').and('contain', 'Edit')
        cy.get('[data-testid="btn-delete"]').should('be.visible').and('contain', 'Delete')
      }
    })
  })

  it('should navigate to edit page when clicking edit button', () => {
    cy.visit('/tasks')
    
    cy.get('body').then(($body) => {
      if ($body.find('[data-testid^="task-card-"]').length > 0) {
        cy.get('[data-testid^="task-card-"]').first().click()
        cy.get('[data-testid="btn-edit"]').click()
        cy.url().should('include', '/edit')
        cy.get('[data-testid="edit-task-view"]').should('be.visible')
      }
    })
  })

  it('should navigate back to tasks when clicking back button', () => {
    cy.visit('/tasks')
    
    cy.get('body').then(($body) => {
      if ($body.find('[data-testid^="task-card-"]').length > 0) {
        cy.get('[data-testid^="task-card-"]').first().click()
        cy.get('[data-testid="btn-back"]').click()
        cy.url().should('include', '/tasks')
        cy.get('[data-testid="tasks-view"]').should('be.visible')
      }
    })
  })

  it('should display task details section', () => {
    cy.visit('/tasks')
    
    cy.get('body').then(($body) => {
      if ($body.find('[data-testid^="task-card-"]').length > 0) {
        cy.get('[data-testid^="task-card-"]').first().click()
        
        cy.get('[data-testid="details-section"]').should('be.visible')
      }
    })
  })
})

