<template>
  <div class="edit-task-view" data-testid="edit-task-view">
    <div class="view-header">
      <h1 data-testid="edit-task-title">Edit Task</h1>
      <router-link :to="`/tasks/${taskId}`" class="btn btn-secondary" data-testid="btn-cancel">
        ← Back
      </router-link>
    </div>

    <div v-if="taskStore.loading && !task" class="loading" data-testid="loading">
      Loading task...
    </div>

    <div v-else-if="taskStore.error && !task" class="error" data-testid="error">
      {{ taskStore.error }}
    </div>

    <div v-else-if="task" class="form-container">
      <form @submit.prevent="handleSubmit" class="task-form" data-testid="task-form">
        <div class="form-group">
          <label for="title" class="label" data-testid="label-title">Title *</label>
          <input
            id="title"
            v-model="form.title"
            type="text"
            class="input"
            required
            maxlength="200"
            data-testid="input-title"
          />
        </div>

        <div class="form-group">
          <label for="description" class="label" data-testid="label-description">Description</label>
          <textarea
            id="description"
            v-model="form.description"
            class="input"
            rows="4"
            maxlength="1000"
            data-testid="input-description"
          />
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="dueDate" class="label" data-testid="label-due-date">Due Date</label>
            <input
              id="dueDate"
              v-model="form.dueDate"
              type="datetime-local"
              class="input"
              data-testid="input-due-date"
            />
          </div>

          <div class="form-group">
            <label for="priority" class="label" data-testid="label-priority">Priority *</label>
            <select
              id="priority"
              v-model="form.priority"
              class="input"
              required
              data-testid="select-priority"
            >
              <option value="LOW">Low</option>
              <option value="MEDIUM">Medium</option>
              <option value="HIGH">High</option>
            </select>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="status" class="label" data-testid="label-status">Status</label>
            <select
              id="status"
              v-model="form.status"
              class="input"
              data-testid="select-status"
            >
              <option value="OPEN">Open</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>
          </div>

          <div class="form-group">
            <label for="category" class="label" data-testid="label-category">Category</label>
            <input
              id="category"
              v-model="form.category"
              type="text"
              class="input"
              maxlength="50"
              data-testid="input-category"
            />
          </div>
        </div>

        <div class="form-actions" data-testid="form-actions">
          <button
            type="submit"
            class="btn btn-primary"
            :disabled="taskStore.loading"
            data-testid="btn-submit"
          >
            {{ taskStore.loading ? 'Saving...' : 'Save Changes' }}
          </button>
          <router-link :to="`/tasks/${taskId}`" class="btn btn-secondary" data-testid="btn-cancel-form">
            Cancel
          </router-link>
        </div>

        <div v-if="error" class="error-message" data-testid="error-message">
          {{ error }}
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTaskStore } from '@/stores/taskStore'
import type { TaskUpdateRequest, TaskPriority, TaskStatus } from '@/types'

const route = useRoute()
const router = useRouter()
const taskStore = useTaskStore()
const error = ref<string | null>(null)

const taskId = parseInt(route.params.id as string, 10)
const task = ref(taskStore.tasks.find(t => t.id === taskId))

const form = ref<TaskUpdateRequest>({
  title: '',
  description: '',
  dueDate: '',
  priority: 'MEDIUM' as TaskPriority,
  status: 'OPEN' as TaskStatus,
  category: ''
})

// Load task if not in store
onMounted(async () => {
  if (!task.value) {
    try {
      const loadedTask = await taskStore.fetchTask(taskId)
      task.value = loadedTask
    } catch (err) {
      console.error('Failed to load task:', err)
    }
  }
  
  if (task.value) {
    form.value = {
      title: task.value.title,
      description: task.value.description || '',
      dueDate: task.value.dueDate ? formatDateTimeLocal(task.value.dueDate) : '',
      priority: task.value.priority,
      status: task.value.status,
      category: task.value.category || ''
    }
  }
})

// Watch for task updates
watch(() => taskStore.tasks, (tasks) => {
  const updatedTask = tasks.find(t => t.id === taskId)
  if (updatedTask) {
    task.value = updatedTask
  }
}, { deep: true })

function formatDateTimeLocal(dateString: string): string {
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day}T${hours}:${minutes}`
}

async function handleSubmit() {
  error.value = null
  
  try {
    const updateData: TaskUpdateRequest = {
      ...form.value,
      dueDate: form.value.dueDate || undefined
    }
    
    await taskStore.updateTask(taskId, updateData)
    router.push(`/tasks/${taskId}`)
  } catch (err: any) {
    error.value = err.message || 'Failed to update task'
  }
}
</script>

<style scoped>
.edit-task-view {
  max-width: 800px;
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

.form-container {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-md);
}

.task-form {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--spacing-md);
}

.form-group {
  display: flex;
  flex-direction: column;
}

textarea.input {
  resize: vertical;
  min-height: 100px;
}

.form-actions {
  display: flex;
  gap: var(--spacing-md);
  margin-top: var(--spacing-md);
}

.error-message {
  color: var(--danger-color);
  padding: var(--spacing-md);
  background-color: #fee2e2;
  border-radius: var(--radius-md);
  margin-top: var(--spacing-md);
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
  .form-row {
    grid-template-columns: 1fr;
  }

  .form-actions {
    flex-direction: column;
  }

  .form-actions .btn {
    width: 100%;
  }
}
</style>

