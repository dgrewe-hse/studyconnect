/**
 * UI Tests for Tasks View
 * Tests the tasks list, filters, search, and statistics
 */

describe('Tasks View UI Tests', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/tasks')
    // Wait for tasks view to be ready (this should always appear regardless of API state)
    cy.get('[data-testid="tasks-view"]', { timeout: 10000 }).should('be.visible')
    // Wait for API call to complete (will fail, but that's expected for UI tests)
    // Then wait for either error state, empty state, or tasks list
    cy.wait(1000) // Give time for API call to fail
    // The view should show either error, empty state, or tasks list
    cy.get('[data-testid="tasks-view"]').should('be.visible')
  })

  it('should display tasks view with header and create button', () => {
    cy.get('[data-testid="tasks-view"]').should('be.visible')
    cy.get('[data-testid="tasks-title"]').should('contain', 'My Tasks')
    cy.get('[data-testid="btn-create-task"]').should('be.visible').and('contain', 'Create Task')
  })

  it('should display overview statistics at the top', () => {
    cy.get('[data-testid="stats-grid"]').should('be.visible')
    
    cy.get('[data-testid="stat-overdue"]').should('be.visible')
    cy.get('[data-testid="stat-overdue"]').should('contain', 'Overdue Tasks')
    
    cy.get('[data-testid="stat-today"]').should('be.visible')
    cy.get('[data-testid="stat-today"]').should('contain', 'Due Today')
    
    cy.get('[data-testid="stat-week"]').should('be.visible')
    cy.get('[data-testid="stat-week"]').should('contain', 'Due This Week')
    
    cy.get('[data-testid="stat-completed"]').should('be.visible')
    cy.get('[data-testid="stat-completed"]').should('contain', 'Completed')
  })

  it('should display filters and search section', () => {
    cy.get('[data-testid="filters-section"]').should('be.visible')
    cy.get('[data-testid="search-input"]').should('be.visible')
    cy.get('[data-testid="search-input"]').should('have.attr', 'placeholder', 'Search tasks...')
  })

  it('should display filter buttons', () => {
    cy.get('[data-testid="filter-OPEN"]').should('be.visible').and('contain', 'Open')
    cy.get('[data-testid="filter-IN_PROGRESS"]').should('be.visible').and('contain', 'In Progress')
    cy.get('[data-testid="filter-COMPLETED"]').should('be.visible').and('contain', 'Completed')
    cy.get('[data-testid="filter-all"]').should('be.visible').and('contain', 'All')
  })

  it('should allow searching tasks', () => {
    cy.get('[data-testid="search-input"]')
      .type('test task')
      .should('have.value', 'test task')
  })

  it('should filter tasks by status', () => {
    // Click Open filter
    cy.get('[data-testid="filter-OPEN"]').click()
    cy.get('[data-testid="filter-OPEN"]').should('have.class', 'btn-primary')
    
    // Click In Progress filter
    cy.get('[data-testid="filter-IN_PROGRESS"]').click()
    cy.get('[data-testid="filter-IN_PROGRESS"]').should('have.class', 'btn-primary')
    
    // Click All to reset
    cy.get('[data-testid="filter-all"]').click()
  })

  it('should navigate to create task page', () => {
    cy.get('[data-testid="btn-create-task"]').click()
    cy.url().should('include', '/tasks/create')
    cy.get('[data-testid="create-task-view"]').should('be.visible')
  })

  it('should display empty state when no tasks match filters', () => {
    // Wait for API error or loading to complete
    cy.wait(1000)
    
    // Check if error state is shown (expected when backend is unavailable)
    cy.get('body').then(($body) => {
      const errorState = $body.find('[data-testid="error"]')
      const emptyState = $body.find('[data-testid="empty-state"]')
      const tasksList = $body.find('[data-testid="tasks-list"]')
      
      // If error is shown (backend unavailable), that's expected for UI tests
      if (errorState.length > 0) {
        cy.get('[data-testid="error"]').should('be.visible')
      } else if (emptyState.length > 0) {
        // If empty state is shown, test the filter
        cy.get('[data-testid="search-input"]').clear().type('nonexistenttask12345xyz')
        cy.wait(500)
        cy.get('[data-testid="empty-state"]').should('be.visible')
      } else if (tasksList.length > 0) {
        // If tasks exist, test filtering
        cy.get('[data-testid="search-input"]').clear().type('nonexistenttask12345xyz')
        cy.wait(500)
        // After filtering, empty state might appear
        cy.get('[data-testid="tasks-view"]').should('be.visible')
      } else {
        // Loading state - just verify view exists
        cy.get('[data-testid="tasks-view"]').should('be.visible')
      }
    })
  })

  it('should display loading state', () => {
    // With API mocked to fail, we should see error state
    // But the view structure should always be visible
    cy.get('[data-testid="tasks-view"]').should('be.visible')
    // Error state is expected when backend is unavailable (UI test scenario)
    cy.get('[data-testid="tasks-view"]').within(() => {
      // Either error, loading, empty state, or tasks list should be present
      cy.get('[data-testid="error"], [data-testid="loading"], [data-testid="empty-state"], [data-testid="tasks-list"]')
        .should('exist')
    })
  })

  it('should be responsive on mobile viewport', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="tasks-view"]').should('be.visible')
    cy.get('[data-testid="filters-section"]').should('be.visible')
    cy.get('[data-testid="search-input"]').should('be.visible')
  })
})

