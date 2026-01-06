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
const typeArg = args.find(arg => arg.startsWith('--type='))
const testType = typeArg ? typeArg.split('=')[1] : null // 'ui' or 'e2e'

// Import test functions
const uiTestModules = {
  login: () => import('./tests/ui/login.test.js').then(m => m.runLoginTests()),
  navigation: () => import('./tests/ui/navigation.test.js').then(m => m.runNavigationTests()),
  dashboard: () => import('./tests/ui/dashboard.test.js').then(m => m.runDashboardTests()),
  tasks: () => import('./tests/ui/tasks.test.js').then(m => m.runTasksTests()),
}

const e2eTestModules = {
  'uc01-create-task': () => import('./tests/e2e/uc01-create-task.test.js').then(m => m.runUC01E2ETests()),
}

const testModules = { ...uiTestModules, ...e2eTestModules }

async function runTests() {
  try {
    // Determine which test modules to use
    const modulesToRun = testType === 'e2e' ? e2eTestModules : 
                        testType === 'ui' ? uiTestModules : 
                        testModules // default: all
    
    if (suite) {
      // Run specific test suite
      const testRunner = modulesToRun[suite]
      if (testRunner) {
        console.log(`\nRunning test suite: ${suite} (${testType || 'all'})\n`)
        await testRunner()
        console.log(`\n✅ Test suite '${suite}' completed\n`)
      } else {
        console.error(`❌ Test suite '${suite}' not found`)
        console.log(`Available ${testType || ''} suites:`, Object.keys(modulesToRun).join(', '))
        process.exit(1)
      }
    } else {
      // Run all tests of the specified type
      const typeLabel = testType === 'e2e' ? 'E2E' : testType === 'ui' ? 'UI' : 'all'
      console.log(`\nRunning all ${typeLabel} tests...\n`)
      for (const [suiteName, testRunner] of Object.entries(modulesToRun)) {
        console.log(`\n${'='.repeat(50)}`)
        console.log(`Running: ${suiteName}`)
        console.log('='.repeat(50))
        await testRunner()
      }
      console.log(`\n✅ All ${typeLabel} tests completed\n`)
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

