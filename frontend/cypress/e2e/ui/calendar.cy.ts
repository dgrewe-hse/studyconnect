/**
 * UI Tests for Calendar View
 * Tests the calendar display and navigation
 */

describe('Calendar View UI Tests', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/calendar')
  })

  it('should display calendar view with header', () => {
    cy.get('[data-testid="calendar-view"]').should('be.visible')
    cy.get('[data-testid="calendar-title"]').should('contain', 'Calendar')
  })

  it('should display calendar navigation buttons', () => {
    cy.get('[data-testid="btn-today"]').should('be.visible').and('contain', 'Today')
    cy.get('[data-testid="btn-prev-month"]').should('be.visible')
    cy.get('[data-testid="btn-next-month"]').should('be.visible')
  })

  it('should display calendar grid', () => {
    cy.get('[data-testid="calendar-container"]').should('be.visible')
    cy.get('[data-testid="calendar-header"]').should('be.visible')
    cy.get('[data-testid="calendar-grid"]').should('be.visible')
  })

  it('should display day headers', () => {
    cy.get('[data-testid="day-header"]').should('have.length', 7)
    cy.get('[data-testid="day-header"]').first().should('contain', 'Sun')
  })

  it('should display calendar days', () => {
    cy.get('[data-testid^="calendar-day-"]').should('have.length.at.least', 28) // At least 4 weeks
  })

  it('should highlight today in calendar', () => {
    // Find today's date
    const today = new Date()
    const todayStr = today.toISOString().split('T')[0]
    
    cy.get(`[data-testid="calendar-day-${todayStr}"]`)
      .should('exist')
      .and('have.class', 'today')
  })

  it('should navigate to previous month', () => {
    cy.get('[data-testid="calendar-header"]').then(($header) => {
      const currentMonth = $header.text()
      
      cy.get('[data-testid="btn-prev-month"]').click()
      
      cy.get('[data-testid="calendar-header"]').should(($newHeader) => {
        expect($newHeader.text()).not.to.eq(currentMonth)
      })
    })
  })

  it('should navigate to next month', () => {
    cy.get('[data-testid="calendar-header"]').then(($header) => {
      const currentMonth = $header.text()
      
      cy.get('[data-testid="btn-next-month"]').click()
      
      cy.get('[data-testid="calendar-header"]').should(($newHeader) => {
        expect($newHeader.text()).not.to.eq(currentMonth)
      })
    })
  })

  it('should navigate to today when clicking today button', () => {
    cy.get('[data-testid="btn-prev-month"]').click()
    cy.get('[data-testid="btn-today"]').click()
    
    // Should show current month
    const currentMonth = new Date().toLocaleDateString('en-US', { month: 'long', year: 'numeric' })
    cy.get('[data-testid="calendar-header"]').should('contain', currentMonth)
  })

  it('should display upcoming tasks section', () => {
    cy.get('[data-testid="tasks-list-section"]').should('be.visible')
    cy.get('[data-testid="tasks-list-section"]').should('contain', 'Upcoming Tasks')
  })

  it('should be responsive on mobile viewport', () => {
    cy.viewport(375, 667)
    
    cy.get('[data-testid="calendar-view"]').should('be.visible')
    cy.get('[data-testid="calendar-grid"]').should('be.visible')
  })
})

