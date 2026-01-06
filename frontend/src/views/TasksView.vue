<template>
  <div class="tasks-view" data-testid="tasks-view">
    <div class="view-header">
      <h1 data-testid="tasks-title">My Tasks</h1>
      <router-link to="/tasks/create" class="btn btn-primary" data-testid="btn-create-task">
        Create Task
      </router-link>
    </div>

    <!-- Overview Stats -->
    <div class="stats-grid" data-testid="stats-grid">
      <div class="stat-card" data-testid="stat-overdue">
        <div class="stat-content">
          <div class="stat-value">{{ taskStore.overdueTasks.length }}</div>
          <div class="stat-label">Overdue Tasks</div>
        </div>
      </div>
      
      <div class="stat-card" data-testid="stat-today">
        <div class="stat-content">
          <div class="stat-value">{{ taskStore.dueTodayTasks.length }}</div>
          <div class="stat-label">Due Today</div>
        </div>
      </div>
      
      <div class="stat-card" data-testid="stat-week">
        <div class="stat-content">
          <div class="stat-value">{{ taskStore.dueThisWeekTasks.length }}</div>
          <div class="stat-label">Due This Week</div>
        </div>
      </div>
      
      <div class="stat-card" data-testid="stat-completed">
        <div class="stat-content">
          <div class="stat-value">{{ taskStore.tasksByStatus.completed.length }}</div>
          <div class="stat-label">Completed</div>
        </div>
      </div>
    </div>

    <!-- Filters and Search -->
    <div class="filters-section" data-testid="filters-section">
      <div class="search-box">
        <input
          v-model="searchQuery"
          type="text"
          placeholder="Search tasks..."
          class="input"
          data-testid="search-input"
        />
      </div>
      
      <div class="filter-buttons">
        <button
          v-for="status in statusFilters"
          :key="status.value"
          class="btn"
          :class="{ 'btn-primary': selectedStatus === status.value, 'btn-secondary': selectedStatus !== status.value }"
          :data-testid="`filter-${status.value}`"
          @click="selectedStatus = status.value"
        >
          {{ status.label }}
        </button>
        <button
          class="btn btn-secondary"
          :data-testid="`filter-all`"
          @click="selectedStatus = null"
        >
          All
        </button>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="taskStore.loading" class="loading" data-testid="loading">
      Loading tasks...
    </div>

    <!-- Error State -->
    <div v-else-if="taskStore.error" class="error" data-testid="error">
      {{ taskStore.error }}
      <button class="btn btn-secondary" @click="taskStore.fetchTasks()">Retry</button>
    </div>

    <!-- Empty State -->
    <div v-else-if="filteredTasks.length === 0" class="empty-state" data-testid="empty-state">
      <p>No tasks found. Create your first task to get started!</p>
      <router-link to="/tasks/create" class="btn btn-primary">Create Task</router-link>
    </div>

    <!-- Tasks List -->
    <div v-else class="tasks-list" data-testid="tasks-list">
      <TaskCard
        v-for="task in filteredTasks"
        :key="task.id"
        :task="task"
      />
    </div>

    <!-- Pagination -->
    <div v-if="taskStore.totalPages > 1" class="pagination" data-testid="pagination">
      <button
        class="btn btn-secondary"
        :disabled="taskStore.currentPage === 0"
        data-testid="btn-prev"
        @click="loadPage(taskStore.currentPage - 1)"
      >
        ← Previous
      </button>
      <span class="page-info" data-testid="page-info">
        Page {{ taskStore.currentPage + 1 }} of {{ taskStore.totalPages }}
      </span>
      <button
        class="btn btn-secondary"
        :disabled="taskStore.currentPage >= taskStore.totalPages - 1"
        data-testid="btn-next"
        @click="loadPage(taskStore.currentPage + 1)"
      >
        Next →
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useTaskStore } from '@/stores/taskStore'
import TaskCard from '@/components/TaskCard.vue'
import type { TaskStatus } from '@/types'

const taskStore = useTaskStore()

const searchQuery = ref('')
const selectedStatus = ref<TaskStatus | null>(null)

const statusFilters = [
  { value: 'OPEN' as TaskStatus, label: 'Open' },
  { value: 'IN_PROGRESS' as TaskStatus, label: 'In Progress' },
  { value: 'COMPLETED' as TaskStatus, label: 'Completed' }
]

const filteredTasks = computed(() => {
  let tasks = taskStore.personalTasks

  // Filter by status
  if (selectedStatus.value) {
    tasks = tasks.filter(task => task.status === selectedStatus.value)
  }

  // Filter by search query
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    tasks = tasks.filter(task =>
      task.title.toLowerCase().includes(query) ||
      task.description?.toLowerCase().includes(query) ||
      task.category?.toLowerCase().includes(query)
    )
  }

  return tasks
})

async function loadPage(page: number) {
  await taskStore.fetchTasks(page)
}

onMounted(async () => {
  await taskStore.fetchTasks()
})
</script>

<style scoped>
.tasks-view {
  max-width: 1200px;
  margin: 0 auto;
}

.view-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--spacing-xl);
  flex-wrap: wrap;
  gap: var(--spacing-md);
}

.view-header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--gray-900);
  margin: 0;
}

.filters-section {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
  box-shadow: var(--shadow-md);
  margin-bottom: var(--spacing-xl);
}

.search-box {
  margin-bottom: var(--spacing-md);
}

.filter-buttons {
  display: flex;
  gap: var(--spacing-sm);
  flex-wrap: wrap;
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-xl);
}

.loading,
.error,
.empty-state {
  text-align: center;
  padding: var(--spacing-2xl);
  background: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
}

.error {
  color: var(--danger-color);
}

.empty-state p {
  margin-bottom: var(--spacing-md);
  color: var(--gray-600);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: var(--spacing-md);
  padding: var(--spacing-lg);
  background: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
}

.page-info {
  color: var(--gray-600);
  font-weight: 500;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-xl);
}

.stat-card {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
  box-shadow: var(--shadow-md);
  text-align: center;
}

.stat-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-value {
  font-size: 2rem;
  font-weight: 700;
  color: var(--primary-color);
  line-height: 1;
  margin-bottom: var(--spacing-xs);
}

.stat-label {
  font-size: 0.875rem;
  color: var(--gray-600);
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
  .view-header {
    flex-direction: column;
    align-items: stretch;
  }

  .view-header h1 {
    font-size: 1.5rem;
  }

  .filter-buttons {
    flex-direction: column;
  }

  .filter-buttons .btn {
    width: 100%;
  }
}
</style>

