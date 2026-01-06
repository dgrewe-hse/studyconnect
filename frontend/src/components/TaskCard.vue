<template>
  <div 
    class="task-card" 
    :class="taskCardClasses"
    :data-testid="`task-card-${task.id}`"
    @click="handleClick"
  >
    <div class="task-header">
      <h3 class="task-title" :data-testid="`task-title-${task.id}`">{{ task.title }}</h3>
      <div class="task-badges">
        <span 
          class="badge" 
          :class="priorityBadgeClass"
          :data-testid="`task-priority-${task.id}`"
        >
          {{ task.priority }}
        </span>
        <span 
          class="badge" 
          :class="statusBadgeClass"
          :data-testid="`task-status-${task.id}`"
        >
          {{ formatStatus(task.status) }}
        </span>
      </div>
    </div>
    
    <p v-if="task.description" class="task-description" :data-testid="`task-description-${task.id}`">
      {{ truncate(task.description, 100) }}
    </p>
    
    <div class="task-meta">
      <div v-if="task.dueDate" class="task-due-date" :data-testid="`task-due-date-${task.id}`">
        <span :class="{ 'overdue': isOverdue, 'due-today': isDueToday }">
          {{ formatDate(task.dueDate) }}
        </span>
      </div>
      
      <div v-if="task.category" class="task-category" :data-testid="`task-category-${task.id}`">
        {{ task.category }}
      </div>
      
      <div v-if="task.group" class="task-group" :data-testid="`task-group-${task.id}`">
        {{ task.group.name }}
      </div>
      
      <div v-if="task.assignedTo" class="task-assigned" :data-testid="`task-assigned-${task.id}`">
        {{ task.assignedTo.name }}
      </div>
    </div>
    
    <div class="task-actions">
      <button 
        v-if="task.status !== 'COMPLETED'"
        class="btn btn-success btn-sm"
        :data-testid="`task-complete-btn-${task.id}`"
        @click.stop="handleComplete"
      >
        Complete
      </button>
      <router-link 
        :to="`/tasks/${task.id}`"
        class="btn btn-secondary btn-sm"
        :data-testid="`task-view-btn-${task.id}`"
        @click.stop
      >
        View
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useTaskStore } from '@/stores/taskStore'
import { format } from 'date-fns'
import type { Task } from '@/types'

const props = defineProps<{
  task: Task
}>()

const router = useRouter()
const taskStore = useTaskStore()

const taskCardClasses = computed(() => {
  return {
    'task-overdue': isOverdue.value,
    'task-due-today': isDueToday.value,
    'task-completed': props.task.status === 'COMPLETED'
  }
})

const priorityBadgeClass = computed(() => {
  switch (props.task.priority) {
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
  switch (props.task.status) {
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
  if (!props.task.dueDate || props.task.status === 'COMPLETED') return false
  return new Date(props.task.dueDate) < new Date()
})

const isDueToday = computed(() => {
  if (!props.task.dueDate || props.task.status === 'COMPLETED') return false
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const dueDate = new Date(props.task.dueDate)
  dueDate.setHours(0, 0, 0, 0)
  return dueDate.getTime() === today.getTime()
})

function formatDate(dateString: string): string {
  try {
    return format(new Date(dateString), 'MMM dd, yyyy HH:mm')
  } catch {
    return dateString
  }
}

function formatStatus(status: string): string {
  return status.replace('_', ' ')
}

function truncate(text: string, maxLength: number): string {
  if (text.length <= maxLength) return text
  return text.substring(0, maxLength) + '...'
}

function handleClick() {
  router.push(`/tasks/${props.task.id}`)
}

async function handleComplete() {
  try {
    await taskStore.updateTaskStatus(props.task.id, 'COMPLETED')
  } catch (error) {
    console.error('Failed to complete task:', error)
  }
}
</script>

<style scoped>
.task-card {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-lg);
  box-shadow: var(--shadow-md);
  cursor: pointer;
  transition: all var(--transition-base);
  border-left: 4px solid transparent;
}

.task-card:hover {
  box-shadow: var(--shadow-lg);
  transform: translateY(-2px);
}

.task-card.task-overdue {
  border-left-color: var(--danger-color);
  background-color: #fef2f2;
}

.task-card.task-due-today {
  border-left-color: var(--warning-color);
  background-color: #fffbeb;
}

.task-card.task-completed {
  opacity: 0.7;
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-sm);
}

.task-title {
  font-size: 1.125rem;
  font-weight: 600;
  color: var(--gray-900);
  margin: 0;
  flex: 1;
}

.task-badges {
  display: flex;
  gap: var(--spacing-xs);
  flex-wrap: wrap;
}

.task-description {
  color: var(--gray-600);
  font-size: 0.875rem;
  margin-bottom: var(--spacing-md);
  line-height: 1.5;
}

.task-meta {
  display: flex;
  flex-wrap: wrap;
  gap: var(--spacing-md);
  margin-bottom: var(--spacing-md);
  font-size: 0.875rem;
  color: var(--gray-600);
}

.task-meta > div {
  display: flex;
  align-items: center;
}

.overdue {
  color: var(--danger-color);
  font-weight: 600;
}

.due-today {
  color: var(--warning-color);
  font-weight: 600;
}

.task-actions {
  display: flex;
  gap: var(--spacing-sm);
  justify-content: flex-end;
}

.btn-sm {
  padding: var(--spacing-xs) var(--spacing-sm);
  font-size: 0.75rem;
}

@media (max-width: 768px) {
  .task-header {
    flex-direction: column;
  }

  .task-actions {
    flex-direction: column;
  }

  .btn-sm {
    width: 100%;
  }
}
</style>

