#!/usr/bin/env node

/**
 * Test runner for Selenium tests
 */

import { fileURLToPath } from 'url'
import { dirname, join } from 'path'
import { readdir } from 'fs/promises'

const __filename = fileURLToPath(import.meta.url)
const __dirname = dirname(__filename)

const args = process.argv.slice(2)
const suiteArg = args.find(arg => arg.startsWith('--suite='))
const suite = suiteArg ? suiteArg.split('=')[1] : null

// Import test functions
const testModules = {
  login: () => import('./tests/ui/login.test.js').then(m => m.runLoginTests()),
  navigation: () => import('./tests/ui/navigation.test.js').then(m => m.runNavigationTests()),
  dashboard: () => import('./tests/ui/dashboard.test.js').then(m => m.runDashboardTests()),
  tasks: () => import('./tests/ui/tasks.test.js').then(m => m.runTasksTests()),
}

async function runTests() {
  try {
    if (suite) {
      // Run specific test suite
      const testRunner = testModules[suite]
      if (testRunner) {
        console.log(`\nRunning test suite: ${suite}\n`)
        await testRunner()
        console.log(`\n✅ Test suite '${suite}' completed\n`)
      } else {
        console.error(`❌ Test suite '${suite}' not found`)
        console.log('Available suites:', Object.keys(testModules).join(', '))
        process.exit(1)
      }
    } else {
      // Run all tests
      console.log('\nRunning all UI tests...\n')
      for (const [suiteName, testRunner] of Object.entries(testModules)) {
        console.log(`\n${'='.repeat(50)}`)
        console.log(`Running: ${suiteName}`)
        console.log('='.repeat(50))
        await testRunner()
      }
      console.log('\n✅ All tests completed\n')
    }
  } catch (error) {
    console.error('\n❌ Error running tests:', error.message)
    if (error.stack) {
      console.error(error.stack)
    }
    process.exit(1)
  }
}

runTests()

