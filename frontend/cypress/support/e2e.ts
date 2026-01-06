// ***********************************************************
// This example support/e2e.ts is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands'

// Import API mocks
import './api-mocks'

// Alternatively you can use CommonJS syntax:
// require('./commands')

/// <reference types="cypress" />

declare global {
  namespace Cypress {
    interface Chainable {
      /**
       * Custom command to login to the application
       * @example cy.login('username', 'password')
       */
      login(username?: string, password?: string): Chainable<void>
      
      /**
       * Custom command to wait for page to be ready
       * @example cy.waitForPageLoad()
       */
      waitForPageLoad(): Chainable<void>
    }
  }
}

// For UI tests, mock API failures by default (backend may not be running)
// This can be overridden in individual tests if needed
beforeEach(() => {
  // Only apply to UI tests (tests in ui/ directory)
  if (Cypress.spec.relative.includes('/ui/')) {
    // Mock API failures to simulate backend being unavailable
    cy.intercept('GET', '**/api/**', { forceNetworkError: true }).as('apiCall')
    cy.intercept('POST', '**/api/**', { forceNetworkError: true }).as('apiCall')
    cy.intercept('PUT', '**/api/**', { forceNetworkError: true }).as('apiCall')
    cy.intercept('PATCH', '**/api/**', { forceNetworkError: true }).as('apiCall')
    cy.intercept('DELETE', '**/api/**', { forceNetworkError: true }).as('apiCall')
  }
})

export {}

