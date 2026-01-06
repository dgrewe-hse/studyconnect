/**
 * Selenium WebDriver Configuration
 */

export const config = {
  baseUrl: process.env.BASE_URL || 'http://localhost:3000',
  browser: process.env.BROWSER || 'chrome',
  // Default to headless in containers/devcontainers
  headless: process.env.HEADLESS === 'true' || process.env.CI || process.env.DEVCONTAINER || !process.env.DISPLAY,
  timeout: 10000,
  implicitWait: 5000,
  pageLoadTimeout: 30000,
}

export const selectors = {
  // Login page
  loginView: '[data-testid="login-view"]',
  loginForm: '[data-testid="login-form"]',
  usernameInput: '[data-testid="input-username"]',
  passwordInput: '[data-testid="input-password"]',
  loginButton: '[data-testid="btn-login"]',
  
  // Navigation
  header: '[data-testid="header"]',
  logo: '[data-testid="logo"]',
  navDashboard: '[data-testid="nav-dashboard"]',
  navTasks: '[data-testid="nav-tasks"]',
  navGroups: '[data-testid="nav-groups"]',
  navCalendar: '[data-testid="nav-calendar"]',
  navProfile: '[data-testid="nav-profile"]',
  
  // Dashboard
  dashboard: '[data-testid="dashboard"]',
  prioritySection: '[data-testid="priority-section"]',
  quickActions: '[data-testid="quick-actions"]',
  btnCreateTask: '[data-testid="btn-create-task"]',
  
  // Tasks
  tasksView: '[data-testid="tasks-view"]',
  statsGrid: '[data-testid="stats-grid"]',
  searchInput: '[data-testid="search-input"]',
  
  // Common
  app: '[data-testid="app"]',
  mainLayout: '[data-testid="main-layout"]',
}

