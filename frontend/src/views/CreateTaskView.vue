<template>
  <div class="create-task-view" data-testid="create-task-view">
    <div class="view-header">
      <h1 data-testid="create-task-title">Create New Task</h1>
      <router-link to="/tasks" class="btn btn-secondary" data-testid="btn-cancel">
        ← Back to Tasks
      </router-link>
    </div>

    <div class="form-container">
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
            placeholder="Enter task title"
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
            placeholder="Enter task description or notes"
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

        <div class="form-group">
          <label for="category" class="label" data-testid="label-category">Category</label>
          <input
            id="category"
            v-model="form.category"
            type="text"
            class="input"
            maxlength="50"
            placeholder="e.g., Mathematics, Exam Prep"
            data-testid="input-category"
          />
        </div>

        <div class="form-actions" data-testid="form-actions">
          <button
            type="submit"
            class="btn btn-primary"
            :disabled="taskStore.loading"
            data-testid="btn-submit"
          >
            {{ taskStore.loading ? 'Creating...' : 'Create Task' }}
          </button>
          <router-link to="/tasks" class="btn btn-secondary" data-testid="btn-cancel-form">
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
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useTaskStore } from '@/stores/taskStore'
import type { TaskCreateRequest, TaskPriority } from '@/types'

const router = useRouter()
const taskStore = useTaskStore()
const error = ref<string | null>(null)

const form = ref<Omit<TaskCreateRequest, 'groupId' | 'assignedToId'>>({
  title: '',
  description: '',
  dueDate: '',
  priority: 'MEDIUM' as TaskPriority,
  category: ''
})

async function handleSubmit() {
  error.value = null
  
  try {
    const taskData: TaskCreateRequest = {
      ...form.value,
      dueDate: form.value.dueDate || undefined
    }
    
    await taskStore.createTask(taskData)
    router.push('/tasks')
  } catch (err: any) {
    error.value = err.message || 'Failed to create task'
  }
}
</script>

<style scoped>
.create-task-view {
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

