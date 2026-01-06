/**
 * UI Tests for Profile Page
 * Tests the profile settings and statistics display
 */

describe('Profile Page UI Tests', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/profile')
  })

  it('should display profile view with header', () => {
    cy.get('[data-testid="profile-view"]').should('be.visible')
    cy.get('[data-testid="profile-title"]').should('contain', 'Profile')
  })

  it('should display user settings section', () => {
    cy.get('[data-testid="user-settings-section"]').should('be.visible')
    cy.get('[data-testid="user-settings-section"]').should('contain', 'User Settings')
    cy.get('[data-testid="user-id-setting"]').should('be.visible')
  })

  it('should display user ID input field', () => {
    cy.get('[data-testid="input-user-id"]').should('be.visible')
    cy.get('[data-testid="input-user-id"]').should('have.attr', 'type', 'number')
    cy.get('[data-testid="input-user-id"]').should('have.attr', 'min', '1')
  })

  it('should allow changing user ID', () => {
    cy.get('[data-testid="input-user-id"]')
      .clear()
      .type('2')
      .should('have.value', '2')
  })

  it('should display statistics section', () => {
    cy.get('[data-testid="stats-section"]').should('be.visible')
    cy.get('[data-testid="stats-section"]').should('contain', 'Your Statistics')
  })

  it('should display all statistics cards', () => {
    cy.get('[data-testid="stat-total-tasks"]').should('be.visible')
    cy.get('[data-testid="stat-total-tasks"]').should('contain', 'Total Tasks')
    
    cy.get('[data-testid="stat-completed"]').should('be.visible')
    cy.get('[data-testid="stat-completed"]').should('contain', 'Completed')
    
    cy.get('[data-testid="stat-in-progress"]').should('be.visible')
    cy.get('[data-testid="stat-in-progress"]').should('contain', 'In Progress')
    
    cy.get('[data-testid="stat-open"]').should('be.visible')
    cy.get('[data-testid="stat-open"]').should('contain', 'Open')
  })

  it('should display numeric values in statistics', () => {
    cy.get('[data-testid="stat-total-tasks"]').within(() => {
      cy.get('.stat-value').should('exist')
    })
    
    cy.get('[data-testid="stat-completed"]').within(() => {
      cy.get('.stat-value').should('exist')
    })
  })

  it('should be responsive on mobile viewport', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="profile-view"]').should('be.visible')
    cy.get('[data-testid="user-settings-section"]').should('be.visible')
    cy.get('[data-testid="stats-section"]').should('be.visible')
  })
})

