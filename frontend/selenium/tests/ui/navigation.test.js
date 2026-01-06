/**
 * Selenium UI Tests for Navigation
 */

import { createDriver, navigateTo, clickElement, elementExists, login } from '../../utils/driver.js'
import { selectors } from '../../config.js'
import assert from 'assert'

async function runNavigationTests() {
  let driver

  try {
    console.log('Starting Navigation UI Tests...')
    
    driver = await createDriver()
    await login(driver)
    
    // Test 1: Display header with logo and navigation
    console.log('  ✓ Testing header elements...')
    await navigateTo(driver, '/')
    
    assert(await elementExists(driver, selectors.header), 'Header should exist')
    assert(await elementExists(driver, selectors.logo), 'Logo should exist')
    assert(await elementExists(driver, selectors.navDashboard), 'Dashboard nav should exist')
    console.log('    ✓ Header elements are present')
    
    // Test 2: Display all navigation links
    console.log('  ✓ Testing navigation links...')
    assert(await elementExists(driver, selectors.navDashboard), 'Dashboard link should exist')
    assert(await elementExists(driver, selectors.navTasks), 'Tasks link should exist')
    assert(await elementExists(driver, selectors.navGroups), 'Groups link should exist')
    assert(await elementExists(driver, selectors.navCalendar), 'Calendar link should exist')
    assert(await elementExists(driver, selectors.navProfile), 'Profile link should exist')
    console.log('    ✓ All navigation links are present')
    
    // Test 3: Navigate to different pages
    console.log('  ✓ Testing navigation...')
    
    // Navigate to Tasks
    await clickElement(driver, selectors.navTasks)
    await driver.sleep(500)
    let url = await driver.getCurrentUrl()
    assert(url.includes('/tasks'), 'Should navigate to tasks page')
    assert(await elementExists(driver, selectors.tasksView), 'Tasks view should be visible')
    
    // Navigate to Groups
    await clickElement(driver, selectors.navGroups)
    await driver.sleep(500)
    url = await driver.getCurrentUrl()
    assert(url.includes('/groups'), 'Should navigate to groups page')
    
    // Navigate to Calendar
    await clickElement(driver, selectors.navCalendar)
    await driver.sleep(500)
    url = await driver.getCurrentUrl()
    assert(url.includes('/calendar'), 'Should navigate to calendar page')
    
    // Navigate to Profile
    await clickElement(driver, selectors.navProfile)
    await driver.sleep(500)
    url = await driver.getCurrentUrl()
    assert(url.includes('/profile'), 'Should navigate to profile page')
    
    // Navigate back to Dashboard
    await clickElement(driver, selectors.navDashboard)
    await driver.sleep(500)
    url = await driver.getCurrentUrl()
    assert(url.endsWith('/') || url.endsWith('/dashboard'), 'Should navigate to dashboard')
    assert(await elementExists(driver, selectors.dashboard), 'Dashboard should be visible')
    console.log('    ✓ Navigation works correctly')
    
    // Test 4: Highlight active navigation link
    console.log('  ✓ Testing active link highlighting...')
    const dashboardLink = await driver.findElement({ css: selectors.navDashboard })
    const classes = await dashboardLink.getAttribute('class')
    assert(classes.includes('active'), 'Dashboard link should be active on dashboard page')
    
    await clickElement(driver, selectors.navTasks)
    await driver.sleep(500)
    
    const tasksLink = await driver.findElement({ css: selectors.navTasks })
    const tasksClasses = await tasksLink.getAttribute('class')
    assert(tasksClasses.includes('active'), 'Tasks link should be active on tasks page')
    console.log('    ✓ Active link highlighting works')
    
    console.log('✅ All navigation tests passed!')
    
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
if (process.argv[1] && process.argv[1].endsWith('navigation.test.js')) {
  runNavigationTests().catch(error => {
    console.error(error)
    process.exit(1)
  })
}

export { runNavigationTests }

