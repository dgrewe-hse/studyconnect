<template>
  <div class="main-layout" data-testid="main-layout">
    <header class="header" data-testid="header">
      <div class="container header-content">
        <div class="logo-section">
          <img 
            src="@/assets/logo.jpeg" 
            alt="StudyConnect Logo" 
            class="logo" 
            data-testid="logo"
          />
          <h1 class="app-title" data-testid="app-title">StudyConnect</h1>
        </div>
        <nav class="nav" data-testid="main-nav">
          <router-link 
            to="/" 
            class="nav-link" 
            data-testid="nav-dashboard"
            :class="{ active: $route.name === 'Dashboard' }"
          >
            Dashboard
          </router-link>
          <router-link 
            to="/tasks" 
            class="nav-link" 
            data-testid="nav-tasks"
            :class="{ active: $route.path.startsWith('/tasks') }"
          >
            Tasks
          </router-link>
          <router-link 
            to="/groups" 
            class="nav-link" 
            data-testid="nav-groups"
            :class="{ active: $route.path.startsWith('/groups') }"
          >
            Groups
          </router-link>
          <router-link 
            to="/calendar" 
            class="nav-link" 
            data-testid="nav-calendar"
            :class="{ active: $route.name === 'Calendar' }"
          >
            Calendar
          </router-link>
          <router-link 
            to="/profile" 
            class="nav-link" 
            data-testid="nav-profile"
            :class="{ active: $route.name === 'Profile' }"
          >
            Profile
          </router-link>
        </nav>
      </div>
    </header>
    
    <main class="main-content" data-testid="main-content">
      <div class="container">
        <router-view />
      </div>
    </main>
    
    <footer class="footer" data-testid="footer">
      <div class="container">
        <p>&copy; 2026 StudyConnect. Student Productivity Tool.</p>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
// Main layout component
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: var(--primary-lighter);
  border-bottom: 2px solid var(--primary-color);
  box-shadow: var(--shadow-sm);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: var(--spacing-md) var(--spacing-md);
  gap: var(--spacing-xl);
}

.logo-section {
  display: flex;
  align-items: center;
  gap: var(--spacing-md);
}

.logo {
  height: 48px;
  width: auto;
  max-width: 200px;
  object-fit: contain;
  display: block;
}

.app-title {
  font-size: 1.5rem;
  font-weight: 700;
  margin: 0;
  /* Gradient text effect - falls back to primary color if not supported */
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  color: var(--primary-color); /* Fallback for browsers without gradient text support */
}

.nav {
  display: flex;
  gap: var(--spacing-sm);
  flex: 1;
  justify-content: center;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: var(--spacing-sm) var(--spacing-md);
  text-decoration: none;
  color: var(--gray-700);
  font-weight: 500;
  border-radius: var(--radius-md);
  transition: all var(--transition-base);
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.6);
  color: var(--primary-color);
}

.nav-link.active {
  background-color: var(--primary-color);
  color: white;
}

.main-content {
  flex: 1;
  padding: var(--spacing-xl) 0;
}

.footer {
  background: white;
  border-top: 1px solid var(--gray-200);
  padding: var(--spacing-md);
  text-align: center;
  color: var(--gray-500);
  font-size: 0.875rem;
}

/* Mobile responsive */
@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    gap: var(--spacing-md);
  }

  .nav {
    flex-wrap: wrap;
    justify-content: center;
  }

  .nav-link {
    font-size: 0.875rem;
    padding: var(--spacing-xs) var(--spacing-sm);
  }

  .app-title {
    font-size: 1.25rem;
  }

  .logo {
    height: 40px;
    max-width: 150px;
  }
}
</style>

