# Selenium WebDriver Tests for StudyConnect

## Prerequisites

1. **Node.js 18+** installed
2. **ChromeDriver** or **GeckoDriver** installed and in PATH
   - ChromeDriver: https://chromedriver.chromium.org/
   - GeckoDriver: https://github.com/mozilla/geckodriver/releases

## Installation

```bash
cd frontend/selenium
npm install
```

## Running in DevContainer

Selenium 4.6+ includes **Selenium Manager** which automatically downloads browser drivers.
However, you still need the browser binary (Chrome/Chromium) installed.

### Option 1: Try installing Chromium

```bash
# Run the installation script (tries multiple methods)
/workspace/INSTALL_SELENIUM_DEPS.sh

# Or try manually:
sudo apt-get update
sudo apt-get install -y chromium  # May not be available in all images
```

### Option 2: Use Firefox (if available)

```bash
sudo apt-get install -y firefox-esr
BROWSER=firefox npm test
```

### Option 3: Use Cypress instead

Cypress tests are already working and don't require browser installation:
```bash
cd frontend
npm run test:cypress:ui:headless
```

### Note on Selenium Manager

Selenium Manager will automatically:
- ✅ Download ChromeDriver (no manual installation needed)
- ✅ Manage driver versions
- ❌ But still requires Chrome/Chromium binary to be installed

The tests will automatically run in headless mode in containers.

## Configuration

Edit `config.js` to configure:
- `baseUrl`: Frontend URL (default: http://localhost:3000)
- `browser`: Browser to use (chrome/firefox)
- `headless`: Run in headless mode (auto-detected in containers)

Or set environment variables:
```bash
export BASE_URL=http://localhost:3000
export BROWSER=chrome
export HEADLESS=false
```

## Running Tests

### Run all UI tests:
```bash
npm test
```

### Run specific test suite:
```bash
npm run test:login
npm run test:navigation
npm run test:dashboard
```

### Run with specific browser:
```bash
BROWSER=firefox npm test
```

### Run in headless mode:
```bash
HEADLESS=true npm test
```

## Test Structure

```
selenium/
├── config.js           # Configuration
├── utils/
│   └── driver.js       # WebDriver utilities
├── tests/
│   └── ui/             # UI test suites
│       ├── login.test.js
│       ├── navigation.test.js
│       ├── dashboard.test.js
│       └── tasks.test.js
└── run-tests.js        # Test runner
```

## Writing Tests

Tests use Node.js ES modules. Example:

```javascript
import { createDriver, navigateTo, waitForVisible } from '../../utils/driver.js'
import assert from 'assert'

async function runMyTests() {
  const driver = await createDriver()
  try {
    await navigateTo(driver, '/')
    // ... test code
  } finally {
    await driver.quit()
  }
}
```

## Notes

- Tests use `data-testid` attributes for reliable element selection
- Make sure the frontend dev server is running before executing tests
- Tests are designed to work with the simplified authentication (any login works)
- In devcontainers, tests automatically run in headless mode
- ChromeDriver is automatically detected in common installation paths

## Troubleshooting

### ChromeDriver not found
```bash
# Install ChromeDriver
sudo apt-get install -y chromium-chromedriver

# Or create symlink if installed in non-standard location
sudo ln -s /usr/lib/chromium-browser/chromedriver /usr/local/bin/chromedriver
```

### Browser binary not found
The driver will automatically search for Chrome/Chromium in common paths:
- `/usr/bin/chromium-browser`
- `/usr/bin/chromium`
- `/usr/bin/google-chrome`

### Tests timeout
- Check that the dev server is running: `npm run dev` (in frontend directory)
- Increase timeout in `config.js` if needed
- Verify network connectivity to the base URL
