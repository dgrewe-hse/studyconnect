/// <reference types="cypress" />

/**
 * Custom command to login to StudyConnect
 * Since authentication is simplified, any credentials work
 */
Cypress.Commands.add('login', (username = 'testuser', password = 'testpass') => {
  cy.visit('/login')
  cy.get('[data-testid="login-view"]', { timeout: 10000 }).should('be.visible')
  cy.get('[data-testid="input-username"]').should('be.visible').type(username)
  cy.get('[data-testid="input-password"]').should('be.visible').type(password)
  cy.get('[data-testid="btn-login"]').should('be.visible').click()
  // Wait for redirect to dashboard
  cy.url({ timeout: 10000 }).should('not.include', '/login')
  cy.get('[data-testid="main-layout"]', { timeout: 10000 }).should('be.visible')
  cy.get('[data-testid="dashboard"]', { timeout: 10000 }).should('be.visible')
})

/**
 * Custom command to wait for page to be fully loaded
 */
Cypress.Commands.add('waitForPageLoad', () => {
  cy.get('[data-testid="app"]').should('be.visible')
  cy.window().its('document.readyState').should('eq', 'complete')
})

