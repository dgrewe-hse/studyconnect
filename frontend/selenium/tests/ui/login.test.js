/**
 * Selenium UI Tests for Login Page
 */

import { createDriver, navigateTo, typeText, clickElement, getText, elementExists } from '../../utils/driver.js'
import { selectors } from '../../config.js'
import assert from 'assert'

async function runLoginTests() {
  let driver

  try {
    console.log('Starting Login Page UI Tests...')
    
    driver = await createDriver()
    
    // Test 1: Display login page with all required elements
    console.log('  ✓ Testing login page elements...')
    await navigateTo(driver, '/login')
    
    assert(await elementExists(driver, selectors.loginView), 'Login view should exist')
    assert(await elementExists(driver, selectors.loginForm), 'Login form should exist')
    assert(await elementExists(driver, selectors.usernameInput), 'Username input should exist')
    assert(await elementExists(driver, selectors.passwordInput), 'Password input should exist')
    assert(await elementExists(driver, selectors.loginButton), 'Login button should exist')
    
    const title = await getText(driver, '[data-testid="login-title"]')
    assert(title.includes('StudyConnect'), 'Should display StudyConnect title')
    console.log('    ✓ All login page elements are present')
    
    // Test 2: Allow typing in username and password fields
    console.log('  ✓ Testing input fields...')
    await typeText(driver, selectors.usernameInput, 'testuser')
    await typeText(driver, selectors.passwordInput, 'testpass')
    
    const usernameValue = await driver.findElement({ css: selectors.usernameInput }).getAttribute('value')
    const passwordValue = await driver.findElement({ css: selectors.passwordInput }).getAttribute('value')
    
    assert.strictEqual(usernameValue, 'testuser', 'Username should be entered')
    assert.strictEqual(passwordValue, 'testpass', 'Password should be entered')
    console.log('    ✓ Input fields work correctly')
    
    // Test 3: Submit form and redirect after login
    console.log('  ✓ Testing login submission...')
    await clickElement(driver, selectors.loginButton)
    
    // Wait for redirect
    await driver.sleep(1000)
    const url = await driver.getCurrentUrl()
    assert(!url.includes('/login'), 'Should redirect away from login page')
    
    // Check dashboard is visible
    assert(await elementExists(driver, selectors.dashboard), 'Dashboard should be visible after login')
    console.log('    ✓ Login redirects to dashboard')
    
    console.log('✅ All login tests passed!')
    
  } catch (error) {
    console.error('❌ Test failed:', error.message)
    throw error
  } finally {
    if (driver) {
      await driver.quit()
    }
  }
}

// Run tests if this file is executed directly
if (process.argv[1] && process.argv[1].endsWith('login.test.js')) {
  runLoginTests().catch(error => {
    console.error(error)
    process.exit(1)
  })
}

export { runLoginTests }

