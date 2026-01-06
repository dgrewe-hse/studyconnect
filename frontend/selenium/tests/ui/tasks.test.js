/**
 * Selenium UI Tests for Tasks View
 */

import { createDriver, navigateTo, clickElement, elementExists, typeText, login } from '../../utils/driver.js'
import { selectors } from '../../config.js'
import assert from 'assert'

async function runTasksTests() {
  let driver

  try {
    console.log('Starting Tasks View UI Tests...')
    
    driver = await createDriver()
    await login(driver)
    
    // Test 1: Display tasks view with header and create button
    console.log('  ✓ Testing tasks view elements...')
    await navigateTo(driver, '/tasks')
    
    assert(await elementExists(driver, selectors.tasksView), 'Tasks view should exist')
    assert(await elementExists(driver, '[data-testid="tasks-title"]'), 'Tasks title should exist')
    assert(await elementExists(driver, selectors.btnCreateTask), 'Create task button should exist')
    console.log('    ✓ Tasks view elements are present')
    
    // Test 2: Display overview statistics
    console.log('  ✓ Testing statistics...')
    assert(await elementExists(driver, selectors.statsGrid), 'Stats grid should exist')
    assert(await elementExists(driver, '[data-testid="stat-overdue"]'), 'Overdue stat should exist')
    assert(await elementExists(driver, '[data-testid="stat-today"]'), 'Today stat should exist')
    assert(await elementExists(driver, '[data-testid="stat-week"]'), 'Week stat should exist')
    assert(await elementExists(driver, '[data-testid="stat-completed"]'), 'Completed stat should exist')
    console.log('    ✓ Statistics are present')
    
    // Test 3: Display filters and search section
    console.log('  ✓ Testing filters and search...')
    assert(await elementExists(driver, '[data-testid="filters-section"]'), 'Filters section should exist')
    assert(await elementExists(driver, selectors.searchInput), 'Search input should exist')
    console.log('    ✓ Filters and search are present')
    
    // Test 4: Display filter buttons
    console.log('  ✓ Testing filter buttons...')
    assert(await elementExists(driver, '[data-testid="filter-OPEN"]'), 'Open filter should exist')
    assert(await elementExists(driver, '[data-testid="filter-IN_PROGRESS"]'), 'In Progress filter should exist')
    assert(await elementExists(driver, '[data-testid="filter-COMPLETED"]'), 'Completed filter should exist')
    assert(await elementExists(driver, '[data-testid="filter-all"]'), 'All filter should exist')
    console.log('    ✓ Filter buttons are present')
    
    // Test 5: Allow searching tasks
    console.log('  ✓ Testing search functionality...')
    await typeText(driver, selectors.searchInput, 'test task')
    await driver.sleep(300)
    
    const value = await driver.findElement({ css: selectors.searchInput }).getAttribute('value')
    assert.strictEqual(value, 'test task', 'Search input should contain typed text')
    console.log('    ✓ Search input works')
    
    // Test 6: Filter tasks by status
    console.log('  ✓ Testing filter functionality...')
    await clickElement(driver, '[data-testid="filter-OPEN"]')
    await driver.sleep(300)
    
    const openFilter = await driver.findElement({ css: '[data-testid="filter-OPEN"]' })
    const classes = await openFilter.getAttribute('class')
    assert(classes.includes('btn-primary'), 'Open filter should be active')
    console.log('    ✓ Filter functionality works')
    
    // Test 7: Navigate to create task page
    console.log('  ✓ Testing create task navigation...')
    await navigateTo(driver, '/tasks')
    await clickElement(driver, selectors.btnCreateTask)
    await driver.sleep(500)
    
    const url = await driver.getCurrentUrl()
    assert(url.includes('/tasks/create'), 'Should navigate to create task page')
    console.log('    ✓ Create task navigation works')
    
    console.log('✅ All tasks tests passed!')
    
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
if (process.argv[1] && process.argv[1].endsWith('tasks.test.js')) {
  runTasksTests().catch(error => {
    console.error(error)
    process.exit(1)
  })
}

export { runTasksTests }

