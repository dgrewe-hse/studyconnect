# UI Tests - No Backend Required

## Overview

The UI tests in `cypress/e2e/ui/` are designed to test the frontend interface **without requiring a backend server**. These tests focus on:

- Visual elements and layout
- User interactions
- Form validation
- Navigation
- Error state handling

## How It Works

### Automatic API Mocking

All UI tests automatically mock API calls to fail (simulating backend unavailability). This is done in `cypress/support/e2e.ts`:

```typescript
beforeEach(() => {
  // Only apply to UI tests (tests in ui/ directory)
  if (Cypress.spec.relative.includes('/ui/')) {
    // Mock API failures to simulate backend being unavailable
    cy.intercept('GET', '**/api/**', { forceNetworkError: true })
    cy.intercept('POST', '**/api/**', { forceNetworkError: true })
    // ... etc
  }
})
```

### Error State Handling

The tests verify that the UI properly handles API failures by checking for:
- Error messages (`[data-testid="error"]`)
- Loading states (`[data-testid="loading"]`)
- Empty states (`[data-testid="empty-state"]`)
- UI structure that should always be present

### Example Test Pattern

```typescript
it('should display tasks view', () => {
  cy.get('body').then(($body) => {
    const errorState = $body.find('[data-testid="error"]')
    const tasksList = $body.find('[data-testid="tasks-list"]')
    
    if (errorState.length > 0) {
      // Backend unavailable - error state is expected for UI tests
      cy.get('[data-testid="error"]').should('be.visible')
    } else if (tasksList.length > 0) {
      // Tasks exist - verify structure
      cy.get('[data-testid="tasks-list"]').should('exist')
    } else {
      // Empty state - also valid
      cy.get('[data-testid="empty-state"]').should('exist')
    }
  })
})
```

## Running UI Tests

```bash
# Run all UI tests (no backend needed)
npm run test:cypress:ui:headless

# Run specific UI test
npm run test:cypress -- --spec "cypress/e2e/ui/login.cy.ts"
```

## End-to-End Tests

For tests that require a backend server, create them in a separate directory (e.g., `cypress/e2e/e2e/`) and they will **not** have API mocking applied.

## Benefits

1. **Faster execution** - No need to wait for backend responses
2. **Isolated testing** - Frontend can be tested independently
3. **CI/CD friendly** - No need to set up backend infrastructure
4. **Error handling verification** - Tests verify UI handles failures gracefully

## Test Structure

- ✅ **UI Tests** (`cypress/e2e/ui/`) - No backend required
- 🔄 **E2E Tests** (`cypress/e2e/e2e/`) - Backend required (to be created)

