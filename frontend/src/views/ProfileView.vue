<template>
  <div class="profile-view" data-testid="profile-view">
    <div class="view-header">
      <h1 data-testid="profile-title">Profile</h1>
    </div>

    <div class="profile-content">
      <div class="profile-section" data-testid="user-settings-section">
        <h2>User Settings</h2>
        <div class="setting-item" data-testid="user-id-setting">
          <label class="setting-label">User ID (for API testing)</label>
          <div class="setting-value">
            <input
              v-model.number="userId"
              type="number"
              class="input"
              min="1"
              data-testid="input-user-id"
              @change="updateUserId"
            />
            <p class="setting-hint">This is used as the X-User-Id header for API requests</p>
          </div>
        </div>
      </div>

      <div class="profile-section" data-testid="stats-section">
        <h2>Your Statistics</h2>
        <div class="stats-grid">
          <div class="stat-item" data-testid="stat-total-tasks">
            <div class="stat-value">{{ taskStore.tasks.length }}</div>
            <div class="stat-label">Total Tasks</div>
          </div>
          <div class="stat-item" data-testid="stat-completed">
            <div class="stat-value">{{ taskStore.tasksByStatus.completed.length }}</div>
            <div class="stat-label">Completed</div>
          </div>
          <div class="stat-item" data-testid="stat-in-progress">
            <div class="stat-value">{{ taskStore.tasksByStatus.inProgress.length }}</div>
            <div class="stat-label">In Progress</div>
          </div>
          <div class="stat-item" data-testid="stat-open">
            <div class="stat-value">{{ taskStore.tasksByStatus.open.length }}</div>
            <div class="stat-label">Open</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useTaskStore } from '@/stores/taskStore'
import { setCurrentUserId } from '@/services/api'

const taskStore = useTaskStore()
const userId = ref<number>(1)

onMounted(async () => {
  const storedUserId = localStorage.getItem('userId')
  if (storedUserId) {
    userId.value = parseInt(storedUserId, 10)
  }
  
  if (taskStore.tasks.length === 0) {
    await taskStore.fetchTasks()
  }
})

function updateUserId() {
  if (userId.value && userId.value > 0) {
    setCurrentUserId(userId.value)
    // Reload tasks with new user ID
    taskStore.fetchTasks()
  }
}
</script>

<style scoped>
.profile-view {
  max-width: 800px;
  margin: 0 auto;
}

.view-header {
  margin-bottom: var(--spacing-xl);
}

.view-header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--gray-900);
  margin: 0;
}

.profile-content {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xl);
}

.profile-section {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-md);
}

.profile-section h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--gray-900);
  margin-bottom: var(--spacing-lg);
}

.setting-item {
  margin-bottom: var(--spacing-lg);
}

.setting-label {
  display: block;
  font-weight: 500;
  color: var(--gray-700);
  margin-bottom: var(--spacing-sm);
}

.setting-value {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xs);
}

.setting-hint {
  font-size: 0.875rem;
  color: var(--gray-500);
  margin: 0;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: var(--spacing-md);
}

.stat-item {
  text-align: center;
  padding: var(--spacing-lg);
  background: var(--gray-50);
  border-radius: var(--radius-md);
}

.stat-value {
  font-size: 2rem;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: var(--spacing-xs);
}

.stat-label {
  font-size: 0.875rem;
  color: var(--gray-600);
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>

