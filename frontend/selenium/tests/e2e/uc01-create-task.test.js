/**
 * Selenium E2E Test: UC01 Create Personal Task
 * Based on backend BDD feature: UC01_Create_Personal_Task.feature
 * 
 * This test requires the backend to be running
 */

import { createDriver, navigateTo, typeText, clickElement, elementExists, getText, waitForVisible } from '../../utils/driver.js'
import { selectors, config } from '../../config.js'
import { login } from '../../utils/driver.js'
import { By, until } from 'selenium-webdriver'
import assert from 'assert'

async function runUC01E2ETests() {
  let driver

  try {
    console.log('Starting UC01 E2E Tests (Create Personal Task)...')
    
    driver = await createDriver()
    
    // Capture browser console logs for debugging
    driver.manage().logs().get('browser').then((logs) => {
      logs.forEach((log) => {
        if (log.level.name === 'SEVERE') {
          console.error('    Browser console error:', log.message)
        }
      })
    }).catch(() => {
      // Ignore if logs not available
    })
    
    // Login and ensure we're on dashboard
    console.log('  ✓ Logging in...')
    await login(driver)
    await driver.sleep(1000) // Wait for dashboard to load
    
    // Verify userId is set in localStorage
    const userId = await driver.executeScript('return localStorage.getItem("userId")')
    console.log('    ✓ UserId in localStorage:', userId)
    if (!userId || userId !== '1') {
      throw new Error(`Expected userId to be '1' in localStorage, but got: ${userId}`)
    }
    
    // Test backend connectivity first
    console.log('  ✓ Testing backend connectivity...')
    try {
      const healthCheck = await driver.executeAsyncScript(`
        const callback = arguments[arguments.length - 1];
        fetch('http://localhost:8080/api/health/ping')
          .then(res => res.json())
          .then(data => callback({success: true, data: data}))
          .catch(err => callback({success: false, error: err.message}));
      `)
      if (healthCheck.success) {
        console.log('    ✓ Backend is reachable:', healthCheck.data)
      } else {
        console.error('    ❌ Backend connectivity check failed:', healthCheck.error)
        throw new Error(`Backend is not reachable: ${healthCheck.error}`)
      }
    } catch (error) {
      console.error('    ⚠️  Could not verify backend connectivity:', error.message)
      // Continue anyway - might be a CORS issue with fetch
    }
    
    // Navigate to tasks view first to ensure it loads
    console.log('  ✓ Navigating to tasks view...')
    await navigateTo(driver, '/tasks')
    await driver.sleep(2000) // Wait for tasks to load from API
    
    // Verify tasks view exists and is visible
    const tasksViewExists = await elementExists(driver, '[data-testid="tasks-view"]')
    if (!tasksViewExists) {
      const currentUrl = await driver.getCurrentUrl()
      const pageSource = await driver.getPageSource()
      console.error('Current URL:', currentUrl)
      console.error('Page source snippet:', pageSource.substring(0, 500))
      throw new Error('Tasks view should exist before creating task')
    }
    console.log('    ✓ Tasks view is accessible')
    
    // Test: Create a task with required fields
    console.log('  ✓ Testing task creation with required fields...')
    await navigateTo(driver, '/tasks/create')
    await driver.sleep(1000)
    
    assert(await elementExists(driver, '[data-testid="create-task-view"]'), 'Create task view should exist')
    
    await typeText(driver, '[data-testid="input-title"]', 'Read Chapter 3')
    
    // Verify title was entered
    const titleValue = await driver.findElement({ css: '[data-testid="input-title"]' }).getAttribute('value')
    console.log('    ✓ Title entered:', titleValue)
    assert.strictEqual(titleValue, 'Read Chapter 3', 'Title should be set correctly')
    
    // Set due date (use future date)
    const futureDate = new Date()
    futureDate.setDate(futureDate.getDate() + 7)
    const dateStr = futureDate.toISOString().slice(0, 16) // Format: YYYY-MM-DDTHH:mm
    await typeText(driver, '[data-testid="input-due-date"]', dateStr)
    console.log('    ✓ Due date entered:', dateStr)
    
    // Select priority
    const prioritySelect = await driver.findElement({ css: '[data-testid="select-priority"]' })
    await prioritySelect.sendKeys('MEDIUM')
    
    // Verify priority was selected
    const priorityValue = await prioritySelect.getAttribute('value')
    console.log('    ✓ Priority selected:', priorityValue)
    
    // Description field is a textarea
    const descriptionField = await driver.findElement({ css: '[data-testid="input-description"]' })
    await descriptionField.clear()
    await descriptionField.sendKeys('Focus on sections 3.2 and 3.3')
    
    // Verify description was entered
    const descriptionValue = await descriptionField.getAttribute('value')
    console.log('    ✓ Description entered:', descriptionValue)
    
    // Submit
    console.log('    ✓ Submitting task...')
    
    // Verify submit button is enabled
    const submitButton = await driver.findElement({ css: '[data-testid="btn-submit"]' })
    const isEnabled = await submitButton.isEnabled()
    console.log('    ✓ Submit button enabled:', isEnabled)
    if (!isEnabled) {
      throw new Error('Submit button is disabled')
    }
    
    // Check for any error messages before submitting
    const errorBefore = await elementExists(driver, '[data-testid="error-message"]')
    if (errorBefore) {
      const errorText = await getText(driver, '[data-testid="error-message"]')
      console.error('    ⚠️  Error before submit:', errorText)
    }
    
    // Get current URL before submit
    const urlBeforeSubmit = await driver.getCurrentUrl()
    console.log('    ✓ URL before submit:', urlBeforeSubmit)
    
    await clickElement(driver, '[data-testid="btn-submit"]')
    
    // Wait a bit for the API call to start
    await driver.sleep(1000)
    
    // Check for error messages after clicking submit
    const errorAfter = await elementExists(driver, '[data-testid="error-message"]')
    if (errorAfter) {
      const errorText = await getText(driver, '[data-testid="error-message"]')
      console.error('    ❌ Error after submit:', errorText)
      throw new Error(`Task creation failed: ${errorText}`)
    }
    
    // Wait for redirect and API call to complete
    // Check if we're still on create page (might indicate an error)
    await driver.sleep(2000)
    
    const currentUrlBeforeWait = await driver.getCurrentUrl()
    console.log('    Current URL after 2s:', currentUrlBeforeWait)
    
    // Verify redirect to tasks page (with longer timeout for API call)
    try {
      await driver.wait(async () => {
        const url = await driver.getCurrentUrl()
        const redirected = url.includes('/tasks') && !url.includes('/create')
        if (!redirected) {
          // Check for errors while waiting
          const hasError = await elementExists(driver, '[data-testid="error-message"]')
          if (hasError) {
            const errorText = await getText(driver, '[data-testid="error-message"]')
            throw new Error(`Task creation failed: ${errorText}`)
          }
        }
        return redirected
      }, config.timeout * 2) // Double timeout for API call
    } catch (error) {
      // If wait times out, check what page we're on and if there are errors
      const finalUrl = await driver.getCurrentUrl()
      const pageSource = await driver.getPageSource()
      const hasError = await elementExists(driver, '[data-testid="error-message"]')
      
      console.error('    ❌ Redirect timeout. Final URL:', finalUrl)
      console.error('    Page source snippet:', pageSource.substring(0, 1000))
      
      if (hasError) {
        const errorText = await getText(driver, '[data-testid="error-message"]')
        throw new Error(`Task creation failed with error: ${errorText}`)
      }
      
      throw new Error(`Redirect timeout. Still on: ${finalUrl}`)
    }
    
    const url = await driver.getCurrentUrl()
    assert(url.includes('/tasks'), `Should redirect to tasks page, but got: ${url}`)
    
    // Wait for tasks view to be visible and loaded
    await waitForVisible(driver, '[data-testid="tasks-view"]')
    console.log('    ✓ Tasks view is visible')
    
    // Wait a bit more for API call to complete and tasks to render
    await driver.sleep(2000)
    
    // Verify task appears in list
    const pageSource = await driver.getPageSource()
    assert(pageSource.includes('Read Chapter 3'), 'Task should appear in list')
    
    console.log('    ✓ Task created successfully')
    
    // Test: Validate title is required
    console.log('  ✓ Testing title validation...')
    await navigateTo(driver, '/tasks/create')
    await driver.sleep(1000)
    
    // Try to submit without title
    await typeText(driver, '[data-testid="input-due-date"]', '2025-11-05T14:00')
    const prioritySelect2 = await driver.findElement({ css: '[data-testid="select-priority"]' })
    await prioritySelect2.sendKeys('LOW')
    
    await clickElement(driver, '[data-testid="btn-submit"]')
    await driver.sleep(500)
    
    // Should still be on create page (validation prevents submission)
    const url2 = await driver.getCurrentUrl()
    assert(url2.includes('/tasks/create'), 'Should remain on create page if validation fails')
    
    console.log('    ✓ Title validation works')
    
    console.log('✅ All UC01 E2E tests passed!')
    
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
if (process.argv[1] && process.argv[1].endsWith('uc01-create-task.test.js')) {
  runUC01E2ETests().catch(error => {
    console.error(error)
    process.exit(1)
  })
}

export { runUC01E2ETests }

