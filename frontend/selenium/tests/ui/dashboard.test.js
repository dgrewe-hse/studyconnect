/**
 * Selenium UI Tests for Dashboard
 */

import { createDriver, navigateTo, clickElement, elementExists, login } from '../../utils/driver.js'
import { selectors } from '../../config.js'
import assert from 'assert'

async function runDashboardTests() {
  let driver

  try {
    console.log('Starting Dashboard UI Tests...')
    
    driver = await createDriver()
    await login(driver)
    
    // Test 1: Display dashboard with all sections
    console.log('  ✓ Testing dashboard sections...')
    await navigateTo(driver, '/')
    
    assert(await elementExists(driver, selectors.dashboard), 'Dashboard should exist')
    assert(await elementExists(driver, '[data-testid="dashboard-title"]'), 'Dashboard title should exist')
    assert(await elementExists(driver, selectors.prioritySection), 'Priority section should exist')
    assert(await elementExists(driver, selectors.quickActions), 'Quick actions should exist')
    console.log('    ✓ All dashboard sections are present')
    
    // Test 2: Display tasks by priority section
    console.log('  ✓ Testing priority sections...')
    assert(await elementExists(driver, '[data-testid="priority-high"]'), 'High priority column should exist')
    assert(await elementExists(driver, '[data-testid="priority-medium"]'), 'Medium priority column should exist')
    assert(await elementExists(driver, '[data-testid="priority-low"]'), 'Low priority column should exist')
    console.log('    ✓ Priority sections are present')
    
    // Test 3: Display quick action buttons
    console.log('  ✓ Testing quick action buttons...')
    assert(await elementExists(driver, selectors.btnCreateTask), 'Create task button should exist')
    assert(await elementExists(driver, '[data-testid="btn-create-group"]'), 'Create group button should exist')
    console.log('    ✓ Quick action buttons are present')
    
    // Test 4: Navigate to create task page
    console.log('  ✓ Testing create task navigation...')
    await clickElement(driver, selectors.btnCreateTask)
    await driver.sleep(500)
    
    const url = await driver.getCurrentUrl()
    assert(url.includes('/tasks/create'), 'Should navigate to create task page')
    assert(await elementExists(driver, '[data-testid="create-task-view"]'), 'Create task view should be visible')
    console.log('    ✓ Create task navigation works')
    
    // Test 5: Display recent tasks section
    console.log('  ✓ Testing recent tasks section...')
    await navigateTo(driver, '/')
    assert(await elementExists(driver, '[data-testid="recent-tasks-section"]'), 'Recent tasks section should exist')
    assert(await elementExists(driver, '[data-testid="recent-tasks-title"]'), 'Recent tasks title should exist')
    console.log('    ✓ Recent tasks section is present')
    
    console.log('✅ All dashboard tests passed!')
    
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
if (process.argv[1] && process.argv[1].endsWith('dashboard.test.js')) {
  runDashboardTests().catch(error => {
    console.error(error)
    process.exit(1)
  })
}

export { runDashboardTests }

