# Cypress Tests for StudyConnect

## Prerequisites

- Node.js 18+
- Frontend dev server running on http://localhost:3000

## Installation

Cypress is already included in the main frontend package.json. Install dependencies:

```bash
cd frontend
npm install
```

## Running Tests in DevContainer

### Install Cypress Dependencies

If you're running in a devcontainer, you need to install system dependencies first:

```bash
# Run the installation script
/workspace/INSTALL_CYPRESS_DEPS.sh

# Or manually:
sudo apt-get update
sudo apt-get install -y xvfb libgtk-3-0 libgbm-dev libnotify-dev libgconf-2-4 libnss3 libxss1 libasound2 libxtst6 xauth xfonts-base xfonts-75dpi xfonts-100dpi
```

### Running Tests

**Headless mode (recommended for devcontainer):**
```bash
npm run test:cypress:headless
npm run test:cypress:ui:headless
```

**Interactive mode (requires X11 forwarding):**
```bash
npm run test:cypress:open
```

**Standard run (may work if Xvfb is available):**
```bash
npm run test:cypress
npm run test:cypress:ui
```

## Test Structure

```
cypress/
├── e2e/
│   └── ui/              # UI test suites
│       ├── login.cy.ts
│       ├── navigation.cy.ts
│       ├── dashboard.cy.ts
│       ├── tasks.cy.ts
│       ├── create-task.cy.ts
│       ├── calendar.cy.ts
│       ├── profile.cy.ts
│       ├── task-card.cy.ts
│       ├── task-detail.cy.ts
│       └── groups.cy.ts
├── support/
│   ├── commands.ts      # Custom commands
│   └── e2e.ts           # Support file
└── fixtures/             # Test data (if needed)
```

## Custom Commands

The following custom commands are available:

- `cy.login(username, password)` - Login to the application
- `cy.waitForPageLoad()` - Wait for page to be fully loaded

## Writing Tests

Example test:

```typescript
describe('My Feature', () => {
  beforeEach(() => {
    cy.login()
    cy.visit('/my-page')
  })

  it('should do something', () => {
    cy.get('[data-testid="my-element"]').should('be.visible')
  })
})
```

## Configuration

Edit `cypress.config.ts` to modify:
- Base URL
- Viewport size
- Timeouts
- Video/screenshot settings

## Troubleshooting

### DevContainer Issues

**Missing Xvfb error:**
```bash
# Install dependencies
/workspace/INSTALL_CYPRESS_DEPS.sh
```

**Interactive mode doesn't work:**
- Use headless mode: `npm run test:cypress:headless`
- Interactive mode requires X11 forwarding which may not be available

**Tests timeout:**
- Check that the dev server is running: `npm run dev`
- Verify the base URL in `cypress.config.ts`

### General Issues

- **Tests fail with "Cannot find module"**: Run `npm install` in the frontend directory
- **Element not found**: Verify the `data-testid` attribute exists in the component
- **Port conflicts**: Ensure port 3000 is available for the dev server

## Notes

- All tests use `data-testid` attributes for reliable element selection
- Tests are designed to work with simplified authentication
- Make sure backend is running for full functionality tests
- In devcontainers, always use headless mode (`xvfb-run`) for best compatibility
