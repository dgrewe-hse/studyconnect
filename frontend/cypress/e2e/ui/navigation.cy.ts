/**
 * UI Tests for Navigation
 * Tests the main navigation menu and header elements
 */

describe('Navigation UI Tests', () => {
  beforeEach(() => {
    cy.login()
  })

  it('should display header with logo and navigation', () => {
    cy.get('[data-testid="header"]').should('be.visible')
    cy.get('[data-testid="logo"]').should('be.visible')
    cy.get('[data-testid="app-title"]').should('contain', 'StudyConnect')
    cy.get('[data-testid="main-nav"]').should('be.visible')
  })

  it('should display all navigation links', () => {
    cy.get('[data-testid="nav-dashboard"]').should('be.visible').and('contain', 'Dashboard')
    cy.get('[data-testid="nav-tasks"]').should('be.visible').and('contain', 'Tasks')
    cy.get('[data-testid="nav-groups"]').should('be.visible').and('contain', 'Groups')
    cy.get('[data-testid="nav-calendar"]').should('be.visible').and('contain', 'Calendar')
    cy.get('[data-testid="nav-profile"]').should('be.visible').and('contain', 'Profile')
  })

  it('should highlight active navigation link', () => {
    // On dashboard
    cy.visit('/')
    cy.get('[data-testid="nav-dashboard"]').should('have.class', 'active')
    
    // Navigate to tasks
    cy.get('[data-testid="nav-tasks"]').click()
    cy.get('[data-testid="nav-tasks"]').should('have.class', 'active')
    cy.get('[data-testid="nav-dashboard"]').should('not.have.class', 'active')
  })

  it('should navigate to different pages when clicking navigation links', () => {
    // Navigate to Tasks
    cy.get('[data-testid="nav-tasks"]').click()
    cy.url().should('include', '/tasks')
    cy.get('[data-testid="tasks-view"]').should('be.visible')
    
    // Navigate to Groups
    cy.get('[data-testid="nav-groups"]').click()
    cy.url().should('include', '/groups')
    cy.get('[data-testid="groups-view"]').should('be.visible')
    
    // Navigate to Calendar
    cy.get('[data-testid="nav-calendar"]').click()
    cy.url().should('include', '/calendar')
    cy.get('[data-testid="calendar-view"]').should('be.visible')
    
    // Navigate to Profile
    cy.get('[data-testid="nav-profile"]').click()
    cy.url().should('include', '/profile')
    cy.get('[data-testid="profile-view"]').should('be.visible')
    
    // Navigate back to Dashboard
    cy.get('[data-testid="nav-dashboard"]').click()
    cy.url().should('eq', Cypress.config().baseUrl + '/')
    cy.get('[data-testid="dashboard"]').should('be.visible')
  })

  it('should display footer with copyright', () => {
    cy.get('[data-testid="footer"]').should('be.visible')
    cy.get('[data-testid="footer"]').should('contain', '2026')
    cy.get('[data-testid="footer"]').should('contain', 'StudyConnect')
  })

  it('should have responsive navigation on mobile', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="header"]').should('be.visible')
    cy.get('[data-testid="main-nav"]').should('be.visible')
    cy.get('[data-testid="nav-dashboard"]').should('be.visible')
  })
})

