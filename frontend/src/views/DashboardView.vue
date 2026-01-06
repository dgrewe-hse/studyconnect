<template>
  <div class="dashboard" data-testid="dashboard">
    <div class="dashboard-header">
      <h1 data-testid="dashboard-title">Dashboard</h1>
    </div>

    <!-- Tasks by Priority -->
    <div class="section" data-testid="priority-section">
      <h2 data-testid="priority-title">Tasks by Priority</h2>
      <div class="priority-grid">
        <div class="priority-column" data-testid="priority-high">
          <h3 class="priority-title high">High Priority</h3>
          <div class="priority-count">{{ taskStore.tasksByPriority.high.length }}</div>
        </div>
        <div class="priority-column" data-testid="priority-medium">
          <h3 class="priority-title medium">Medium Priority</h3>
          <div class="priority-count">{{ taskStore.tasksByPriority.medium.length }}</div>
        </div>
        <div class="priority-column" data-testid="priority-low">
          <h3 class="priority-title low">Low Priority</h3>
          <div class="priority-count">{{ taskStore.tasksByPriority.low.length }}</div>
        </div>
      </div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions" data-testid="quick-actions">
      <router-link to="/tasks/create" class="btn btn-primary" data-testid="btn-create-task">
        Create New Task
      </router-link>
      <router-link to="/groups/create" class="btn btn-secondary" data-testid="btn-create-group">
        Create Study Group
      </router-link>
    </div>

    <!-- Recent Tasks -->
    <div class="section" data-testid="recent-tasks-section">
      <div class="section-header">
        <h2 data-testid="recent-tasks-title">Recent Tasks</h2>
        <router-link to="/tasks" class="view-all-link" data-testid="view-all-tasks">
          View All →
        </router-link>
      </div>
      
      <div v-if="taskStore.loading" class="loading" data-testid="loading">
        Loading tasks...
      </div>
      
      <div v-else-if="taskStore.error" class="error" data-testid="error">
        {{ taskStore.error }}
      </div>
      
      <div v-else-if="recentTasks.length === 0" class="empty-state" data-testid="empty-state">
        <p>No tasks yet. Create your first task to get started!</p>
      </div>
      
      <div v-else class="tasks-list" data-testid="tasks-list">
        <TaskCard
          v-for="task in recentTasks"
          :key="task.id"
          :task="task"
          data-testid="task-card"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, computed } from 'vue'
import { useTaskStore } from '@/stores/taskStore'
import TaskCard from '@/components/TaskCard.vue'

const taskStore = useTaskStore()

const recentTasks = computed(() => {
  return taskStore.tasks.slice(0, 5)
})

onMounted(async () => {
  if (taskStore.tasks.length === 0) {
    await taskStore.fetchTasks()
  }
})
</script>

<style scoped>
.dashboard {
  max-width: 1200px;
  margin: 0 auto;
}

.dashboard-header {
  margin-bottom: var(--spacing-xl);
}

.dashboard-header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--gray-900);
  margin: 0;
}

.quick-actions {
  display: flex;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-xl);
  flex-wrap: wrap;
}

.section {
  margin-bottom: var(--spacing-xl);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--spacing-md);
}

.section-header h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--gray-900);
}

.view-all-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
  font-size: 0.875rem;
}

.view-all-link:hover {
  text-decoration: underline;
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
}

.loading,
.error,
.empty-state {
  text-align: center;
  padding: var(--spacing-xl);
  color: var(--gray-600);
}

.error {
  color: var(--danger-color);
}

.priority-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: var(--spacing-md);
}

.priority-column {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
  box-shadow: var(--shadow-md);
  text-align: center;
}

.priority-title {
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: var(--spacing-md);
}

.priority-title.high {
  color: var(--danger-color);
}

.priority-title.medium {
  color: var(--warning-color);
}

.priority-title.low {
  color: var(--success-color);
}

.priority-count {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--gray-900);
}

@media (max-width: 768px) {
  .priority-grid {
    grid-template-columns: 1fr;
  }
}
</style>

