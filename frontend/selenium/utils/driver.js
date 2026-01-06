/**
 * WebDriver utility functions
 */

import { Builder, By, until } from 'selenium-webdriver'
import chrome from 'selenium-webdriver/chrome.js'
import firefox from 'selenium-webdriver/firefox.js'
import fs from 'node:fs'
import { config, selectors } from '../config.js'

/**
 * Create and configure WebDriver instance
 * Uses Selenium Manager (built into Selenium 4.6+) to automatically download drivers
 */
export async function createDriver() {
  let options
  
  if (config.browser === 'chrome') {
    options = new chrome.Options()
    
    // Always use headless in containers/devcontainer
    const isContainer = process.env.CI || process.env.DEVCONTAINER || !process.env.DISPLAY
    if (config.headless || isContainer) {
      options.addArguments('--headless=new') // Use new headless mode
    }
    
    // Required for running in containers
    options.addArguments('--no-sandbox')
    options.addArguments('--disable-dev-shm-usage')
    options.addArguments('--disable-gpu')
    options.addArguments('--disable-software-rasterizer')
    options.addArguments('--window-size=1280,720')
    options.addArguments('--disable-extensions')
    options.addArguments('--disable-background-networking')
    options.addArguments('--remote-debugging-port=9222')
    
    // Try to find Chrome/Chromium binary
    // Selenium Manager will handle the driver, but we need the browser binary
    const chromePaths = [
      '/usr/bin/chromium-browser',
      '/usr/bin/chromium',
      '/usr/bin/google-chrome',
      '/usr/bin/google-chrome-stable',
      '/snap/bin/chromium'
    ]
    
    for (const path of chromePaths) {
      try {
        if (fs.existsSync(path)) {
          options.setChromeBinaryPath(path)
          break
        }
      } catch (e) {
        // Continue to next path
      }
    }
    
    // If no Chrome/Chromium found, Selenium Manager will try to download it
    // But for now, we'll let it fail gracefully and provide helpful error
    
  } else if (config.browser === 'firefox') {
    options = new firefox.Options()
    const isContainer = process.env.CI || process.env.DEVCONTAINER || !process.env.DISPLAY
    
    // Always use headless in containers
    if (config.headless || isContainer) {
      options.addArguments('--headless')
    }
    
    // Required for running in containers
    options.addArguments('--no-sandbox')
    options.addArguments('--disable-dev-shm-usage')
    options.addArguments('--disable-gpu')
    
    // Try to find Firefox binary
    const firefoxPaths = [
      '/usr/bin/firefox',
      '/usr/bin/firefox-esr',
      '/snap/bin/firefox'
    ]
    
    for (const path of firefoxPaths) {
      try {
        if (fs.existsSync(path)) {
          options.setBinary(path)
          break
        }
      } catch (e) {
        // Continue to next path
      }
    }
    
    // Set preferences for headless mode
    options.setPreference('dom.ipc.processCount', 1)
    options.setPreference('browser.download.folderList', 2)
    options.setPreference('browser.download.manager.showWhenStarting', false)
    options.setPreference('browser.safebrowsing.enabled', false)
    options.setPreference('browser.safebrowsing.malware.enabled', false)
  }
  
  // Selenium Manager (built into Selenium 4.6+) will automatically:
  // 1. Download the correct driver version
  // 2. Manage driver lifecycle
  // 3. Handle browser binary detection
  // We don't need to manually specify the service anymore
  let builder = new Builder().forBrowser(config.browser)
  
  try {
    if (config.browser === 'chrome') {
      builder = builder.setChromeOptions(options)
    } else if (config.browser === 'firefox') {
      builder = builder.setFirefoxOptions(options)
    }
    
    const driver = await builder.build()
    
    // Set timeouts
    driver.manage().setTimeouts({
      implicit: config.implicitWait,
      pageLoad: config.pageLoadTimeout,
    })
    
    return driver
  } catch (error) {
    // Provide helpful error message
    if (error.message.includes('Process unexpectedly closed') || error.message.includes('signal')) {
      const browserName = config.browser === 'chrome' ? 'Chrome/Chromium' : 'Firefox'
      const installCmd = config.browser === 'chrome' ? 'chromium' : 'firefox-esr'
      
      throw new Error(
        `${browserName} browser process crashed on startup.\n` +
        `This usually means:\n` +
        `1. Browser binary not found or not executable\n` +
        `2. Missing browser dependencies\n` +
        `3. Browser needs additional configuration for containers\n\n` +
        `Try:\n` +
        `- Install missing dependencies: sudo apt-get install -y ${installCmd} libgtk-3-0 libdbus-glib-1-2\n` +
        `- Or use Cypress instead: cd ../.. && npm run test:cypress:ui:headless\n` +
        `- Original error: ${error.message}`
      )
    }
    throw error
  }
}

/**
 * Wait for element to be visible
 */
export async function waitForElement(driver, selector, timeout = config.timeout) {
  return await driver.wait(
    until.elementLocated(By.css(selector)),
    timeout
  )
}

/**
 * Wait for element to be visible and clickable
 */
export async function waitForVisible(driver, selector, timeout = config.timeout) {
  const element = await waitForElement(driver, selector, timeout)
  await driver.wait(until.elementIsVisible(element), timeout)
  return element
}

/**
 * Click element by selector
 */
export async function clickElement(driver, selector) {
  const element = await waitForVisible(driver, selector)
  await element.click()
}

/**
 * Type text into input field
 */
export async function typeText(driver, selector, text) {
  const element = await waitForVisible(driver, selector)
  await element.clear()
  await element.sendKeys(text)
}

/**
 * Get text from element
 */
export async function getText(driver, selector) {
  const element = await waitForVisible(driver, selector)
  return await element.getText()
}

/**
 * Check if element exists
 */
export async function elementExists(driver, selector) {
  try {
    await driver.findElement(By.css(selector))
    return true
  } catch {
    return false
  }
}

/**
 * Navigate to URL
 */
export async function navigateTo(driver, path = '') {
  const url = `${config.baseUrl}${path}`
  await driver.get(url)
}

/**
 * Login helper function
 */
export async function login(driver, username = 'testuser', password = 'testpass') {
  await navigateTo(driver, '/login')
  await typeText(driver, selectors.usernameInput, username)
  await typeText(driver, selectors.passwordInput, password)
  await clickElement(driver, selectors.loginButton)
  
  // Wait for redirect
  await driver.wait(async () => {
    const url = await driver.getCurrentUrl()
    return !url.includes('/login')
  }, config.timeout)
  
  // Wait for dashboard
  await waitForVisible(driver, selectors.dashboard)
}

