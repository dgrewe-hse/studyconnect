<template>
  <div class="task-detail-view" data-testid="task-detail-view">
    <div v-if="taskStore.loading && !task" class="loading" data-testid="loading">
      Loading task...
    </div>

    <div v-else-if="taskStore.error && !task" class="error" data-testid="error">
      {{ taskStore.error }}
    </div>

    <div v-else-if="task" class="task-detail">
      <div class="detail-header">
        <div>
          <h1 data-testid="task-detail-title">{{ task.title }}</h1>
          <div class="task-badges" data-testid="task-badges">
            <span 
              class="badge" 
              :class="priorityBadgeClass"
              data-testid="priority-badge"
            >
              {{ task.priority }}
            </span>
            <span 
              class="badge" 
              :class="statusBadgeClass"
              data-testid="status-badge"
            >
              {{ formatStatus(task.status) }}
            </span>
            <span v-if="task.category" class="badge badge-gray" data-testid="category-badge">
              {{ task.category }}
            </span>
          </div>
        </div>
        <div class="header-actions">
          <router-link 
            :to="`/tasks/${task.id}/edit`" 
            class="btn btn-primary"
            data-testid="btn-edit"
          >
            Edit
          </router-link>
          <button
            v-if="task.status !== 'COMPLETED'"
            class="btn btn-success"
            data-testid="btn-complete"
            @click="handleComplete"
          >
            Complete
          </button>
          <button
            class="btn btn-danger"
            data-testid="btn-delete"
            @click="handleDelete"
          >
            Delete
          </button>
        </div>
      </div>

      <div class="detail-content">
        <div v-if="task.description" class="detail-section" data-testid="description-section">
          <h3>Description</h3>
          <p>{{ task.description }}</p>
        </div>

        <div class="detail-section" data-testid="details-section">
          <h3>Details</h3>
          <div class="detail-grid">
            <div v-if="task.dueDate" class="detail-item" data-testid="due-date-item">
              <span class="detail-label">Due Date:</span>
              <span :class="{ 'overdue': isOverdue, 'due-today': isDueToday }">
                {{ formatDate(task.dueDate) }}
              </span>
            </div>
            <div class="detail-item" data-testid="created-item">
              <span class="detail-label">Created:</span>
              <span>{{ formatDate(task.createdAt) }}</span>
            </div>
            <div v-if="task.createdBy" class="detail-item" data-testid="creator-item">
              <span class="detail-label">Created By:</span>
              <span>{{ task.createdBy.name }}</span>
            </div>
            <div v-if="task.assignedTo" class="detail-item" data-testid="assigned-item">
              <span class="detail-label">Assigned To:</span>
              <span>{{ task.assignedTo.name }}</span>
            </div>
            <div v-if="task.group" class="detail-item" data-testid="group-item">
              <span class="detail-label">Group:</span>
              <span>{{ task.group.name }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="detail-actions">
        <router-link to="/tasks" class="btn btn-secondary" data-testid="btn-back">
          ← Back to Tasks
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTaskStore } from '@/stores/taskStore'
import { format } from 'date-fns'
import type { Task } from '@/types'

const route = useRoute()
const router = useRouter()
const taskStore = useTaskStore()

const taskId = parseInt(route.params.id as string, 10)
const task = ref<Task | undefined>(taskStore.tasks.find(t => t.id === taskId))

const priorityBadgeClass = computed(() => {
  if (!task.value) return ''
  switch (task.value.priority) {
    case 'HIGH':
      return 'badge-danger'
    case 'MEDIUM':
      return 'badge-warning'
    case 'LOW':
      return 'badge-success'
    default:
      return 'badge-gray'
  }
})

const statusBadgeClass = computed(() => {
  if (!task.value) return ''
  switch (task.value.status) {
    case 'COMPLETED':
      return 'badge-success'
    case 'IN_PROGRESS':
      return 'badge-primary'
    case 'OPEN':
      return 'badge-gray'
    default:
      return 'badge-gray'
  }
})

const isOverdue = computed(() => {
  if (!task.value?.dueDate || task.value.status === 'COMPLETED') return false
  return new Date(task.value.dueDate) < new Date()
})

const isDueToday = computed(() => {
  if (!task.value?.dueDate || task.value.status === 'COMPLETED') return false
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const dueDate = new Date(task.value.dueDate)
  dueDate.setHours(0, 0, 0, 0)
  return dueDate.getTime() === today.getTime()
})

onMounted(async () => {
  if (!task.value) {
    try {
      const loadedTask = await taskStore.fetchTask(taskId)
      task.value = loadedTask
    } catch (err) {
      console.error('Failed to load task:', err)
    }
  }
})

watch(() => taskStore.tasks, (tasks) => {
  const updatedTask = tasks.find(t => t.id === taskId)
  if (updatedTask) {
    task.value = updatedTask
  }
}, { deep: true })

function formatDate(dateString: string): string {
  try {
    return format(new Date(dateString), 'PPpp')
  } catch {
    return dateString
  }
}

function formatStatus(status: string): string {
  return status.replace('_', ' ')
}

async function handleComplete() {
  if (!task.value) return
  try {
    await taskStore.updateTaskStatus(task.value.id, 'COMPLETED')
  } catch (error) {
    console.error('Failed to complete task:', error)
  }
}

async function handleDelete() {
  if (!task.value) return
  if (!confirm('Are you sure you want to delete this task?')) return
  
  try {
    await taskStore.deleteTask(task.value.id)
    router.push('/tasks')
  } catch (error) {
    console.error('Failed to delete task:', error)
    alert('Failed to delete task. Please try again.')
  }
}
</script>

<style scoped>
.task-detail-view {
  max-width: 900px;
  margin: 0 auto;
}

.task-detail {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-md);
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: var(--spacing-xl);
  padding-bottom: var(--spacing-lg);
  border-bottom: 2px solid var(--gray-200);
  gap: var(--spacing-md);
  flex-wrap: wrap;
}

.detail-header h1 {
  font-size: 2rem;
  font-weight: 700;
  color: var(--gray-900);
  margin: 0 0 var(--spacing-md) 0;
}

.task-badges {
  display: flex;
  gap: var(--spacing-sm);
  flex-wrap: wrap;
}

.header-actions {
  display: flex;
  gap: var(--spacing-sm);
  flex-wrap: wrap;
}

.detail-content {
  margin-bottom: var(--spacing-xl);
}

.detail-section {
  margin-bottom: var(--spacing-xl);
}

.detail-section h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: var(--gray-900);
  margin-bottom: var(--spacing-md);
}

.detail-section p {
  color: var(--gray-700);
  line-height: 1.6;
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: var(--spacing-md);
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xs);
}

.detail-label {
  font-weight: 600;
  color: var(--gray-600);
  font-size: 0.875rem;
}

.overdue {
  color: var(--danger-color);
  font-weight: 600;
}

.due-today {
  color: var(--warning-color);
  font-weight: 600;
}

.detail-actions {
  padding-top: var(--spacing-lg);
  border-top: 2px solid var(--gray-200);
}

.loading,
.error {
  text-align: center;
  padding: var(--spacing-2xl);
  background: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
}

.error {
  color: var(--danger-color);
}

@media (max-width: 768px) {
  .detail-header {
    flex-direction: column;
  }

  .header-actions {
    width: 100%;
  }

  .header-actions .btn {
    flex: 1;
  }

  .detail-grid {
    grid-template-columns: 1fr;
  }
}
</style>

