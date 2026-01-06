/**
 * UI Tests for Groups View
 * Tests the groups page display
 */

describe('Groups View UI Tests', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/groups')
  })

  it('should display groups view with header', () => {
    cy.get('[data-testid="groups-view"]').should('be.visible')
    cy.get('[data-testid="groups-title"]').should('contain', 'Study Groups')
  })

  it('should display create group button', () => {
    cy.get('[data-testid="btn-create-group"]')
      .should('be.visible')
      .and('contain', 'Create Group')
  })

  it('should display info message about coming soon', () => {
    cy.get('[data-testid="info-message"]').should('be.visible')
    cy.get('[data-testid="info-message"]').should('contain', 'coming soon')
  })

  it('should navigate to create group page', () => {
    cy.get('[data-testid="btn-create-group"]').click()
    cy.url().should('include', '/groups/create')
    cy.get('[data-testid="create-group-view"]').should('be.visible')
  })

  it('should be responsive on mobile viewport', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="groups-view"]').should('be.visible')
    cy.get('[data-testid="btn-create-group"]').should('be.visible')
  })
})

