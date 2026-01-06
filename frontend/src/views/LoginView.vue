<template>
  <div class="login-view" data-testid="login-view">
    <div class="login-container">
      <div class="login-card">
        <div class="logo-section" data-testid="login-logo-section">
          <img 
            src="@/assets/logo.jpeg" 
            alt="StudyConnect Logo" 
            class="login-logo" 
            data-testid="login-logo"
          />
          <h1 class="login-title" data-testid="login-title">StudyConnect</h1>
          <p class="login-subtitle" data-testid="login-subtitle">Student Productivity Tool</p>
        </div>

        <form @submit.prevent="handleLogin" class="login-form" data-testid="login-form">
          <div class="form-group">
            <label for="username" class="label" data-testid="label-username">Username</label>
            <input
              id="username"
              v-model="username"
              type="text"
              class="input"
              placeholder="Enter your username"
              required
              data-testid="input-username"
            />
          </div>

          <div class="form-group">
            <label for="password" class="label" data-testid="label-password">Password</label>
            <input
              id="password"
              v-model="password"
              type="password"
              class="input"
              placeholder="Enter your password"
              required
              data-testid="input-password"
            />
          </div>

          <div v-if="error" class="error-message" data-testid="error-message">
            {{ error }}
          </div>

          <button
            type="submit"
            class="btn btn-primary btn-block"
            :disabled="loading"
            data-testid="btn-login"
          >
            {{ loading ? 'Signing in...' : 'Sign In' }}
          </button>
        </form>

        <div class="login-info" data-testid="login-info">
          <p class="info-text">For testing purposes, any username and password will work.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { setCurrentUserId } from '@/services/api'

const router = useRouter()
const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref<string | null>(null)

async function handleLogin() {
  loading.value = true
  error.value = null

  try {
    // For now, any login works - set a default user ID
    // In a real app, this would authenticate with the backend
    const userId = 1 // Default user ID
    setCurrentUserId(userId)
    
    // Store login state
    localStorage.setItem('isAuthenticated', 'true')
    localStorage.setItem('username', username.value)
    
    // Navigate to dashboard
    router.push('/')
  } catch (err: any) {
    error.value = err.message || 'Login failed. Please try again.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-view {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(180deg, var(--primary-lighter) 0%, white 100%);
  padding: var(--spacing-xl);
}

.login-container {
  width: 100%;
  max-width: 400px;
}

.login-card {
  background: white;
  border-radius: var(--radius-xl);
  padding: var(--spacing-2xl);
  box-shadow: var(--shadow-xl);
}

.logo-section {
  text-align: center;
  margin-bottom: var(--spacing-2xl);
}

.login-logo {
  height: 80px;
  width: auto;
  max-width: 200px;
  object-fit: contain;
  margin-bottom: var(--spacing-md);
}

.login-title {
  font-size: 2rem;
  font-weight: 700;
  color: var(--primary-color);
  margin: 0 0 var(--spacing-xs) 0;
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  color: var(--primary-color);
}

.login-subtitle {
  color: var(--gray-600);
  font-size: 0.875rem;
  margin: 0;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.form-group {
  display: flex;
  flex-direction: column;
}

.btn-block {
  width: 100%;
  padding: var(--spacing-md);
  font-size: 1rem;
  margin-top: var(--spacing-md);
}

.error-message {
  color: var(--danger-color);
  padding: var(--spacing-md);
  background-color: #fee2e2;
  border-radius: var(--radius-md);
  font-size: 0.875rem;
  text-align: center;
}

.login-info {
  margin-top: var(--spacing-xl);
  padding-top: var(--spacing-lg);
  border-top: 1px solid var(--gray-200);
  text-align: center;
}

.info-text {
  color: var(--gray-500);
  font-size: 0.75rem;
  margin: 0;
}

@media (max-width: 768px) {
  .login-view {
    padding: var(--spacing-md);
  }

  .login-card {
    padding: var(--spacing-xl);
  }

  .login-logo {
    height: 60px;
  }

  .login-title {
    font-size: 1.5rem;
  }
}
</style>

