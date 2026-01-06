<template>
  <div class="calendar-view" data-testid="calendar-view">
    <div class="view-header">
      <h1 data-testid="calendar-title">Calendar</h1>
      <div class="header-actions">
        <button class="btn btn-secondary" data-testid="btn-today" @click="goToToday">
          Today
        </button>
        <button class="btn btn-secondary" data-testid="btn-prev-month" @click="previousMonth">
          ←
        </button>
        <button class="btn btn-secondary" data-testid="btn-next-month" @click="nextMonth">
          →
        </button>
      </div>
    </div>

    <div class="calendar-container" data-testid="calendar-container">
      <div class="calendar-header" data-testid="calendar-header">
        <h2>{{ currentMonthYear }}</h2>
      </div>

      <div class="calendar-grid" data-testid="calendar-grid">
        <div class="calendar-day-header" v-for="day in weekDays" :key="day" data-testid="day-header">
          {{ day }}
        </div>
        
        <div
          v-for="day in calendarDays"
          :key="day.date"
          class="calendar-day"
          :class="{ 'other-month': day.otherMonth, 'today': day.isToday }"
          :data-testid="`calendar-day-${day.date}`"
        >
          <div class="day-number">{{ day.day }}</div>
          <div class="day-tasks">
            <div
              v-for="task in day.tasks"
              :key="task.id"
              class="task-dot"
              :class="getTaskPriorityClass(task.priority)"
              :data-testid="`task-${task.id}`"
              :title="task.title"
            />
          </div>
        </div>
      </div>
    </div>

    <div class="tasks-list-section" data-testid="tasks-list-section">
      <h2>Upcoming Tasks</h2>
      <div v-if="upcomingTasks.length === 0" class="empty-state" data-testid="empty-upcoming">
        No upcoming tasks
      </div>
      <div v-else class="tasks-list" data-testid="upcoming-tasks-list">
        <TaskCard
          v-for="task in upcomingTasks"
          :key="task.id"
          :task="task"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useTaskStore } from '@/stores/taskStore'
import TaskCard from '@/components/TaskCard.vue'
import type { Task, TaskPriority } from '@/types'

const taskStore = useTaskStore()
const currentDate = ref(new Date())

const weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']

const currentMonthYear = computed(() => {
  return currentDate.value.toLocaleDateString('en-US', { month: 'long', year: 'numeric' })
})

const calendarDays = computed(() => {
  const year = currentDate.value.getFullYear()
  const month = currentDate.value.getMonth()
  
  // First day of the month
  const firstDay = new Date(year, month, 1)
  const firstDayOfWeek = firstDay.getDay()
  
  // Last day of the month
  const lastDay = new Date(year, month + 1, 0)
  const daysInMonth = lastDay.getDate()
  
  // Previous month days to fill the first week
  const prevMonth = new Date(year, month, 0)
  const daysInPrevMonth = prevMonth.getDate()
  
  const days: Array<{
    date: string
    day: number
    otherMonth: boolean
    isToday: boolean
    tasks: Task[]
  }> = []
  
  // Previous month days
  for (let i = firstDayOfWeek - 1; i >= 0; i--) {
    const day = daysInPrevMonth - i
    const date = new Date(year, month - 1, day)
    days.push({
      date: date.toISOString().split('T')[0],
      day,
      otherMonth: true,
      isToday: false,
      tasks: getTasksForDate(date)
    })
  }
  
  // Current month days
  const today = new Date()
  for (let day = 1; day <= daysInMonth; day++) {
    const date = new Date(year, month, day)
    days.push({
      date: date.toISOString().split('T')[0],
      day,
      otherMonth: false,
      isToday: date.toDateString() === today.toDateString(),
      tasks: getTasksForDate(date)
    })
  }
  
  // Next month days to fill the last week
  const remainingDays = 42 - days.length // 6 weeks * 7 days
  for (let day = 1; day <= remainingDays; day++) {
    const date = new Date(year, month + 1, day)
    days.push({
      date: date.toISOString().split('T')[0],
      day,
      otherMonth: true,
      isToday: false,
      tasks: getTasksForDate(date)
    })
  }
  
  return days
})

const upcomingTasks = computed(() => {
  const now = new Date()
  return taskStore.tasks
    .filter(task => {
      if (!task.dueDate || task.status === 'COMPLETED') return false
      return new Date(task.dueDate) >= now
    })
    .sort((a, b) => {
      if (!a.dueDate || !b.dueDate) return 0
      return new Date(a.dueDate).getTime() - new Date(b.dueDate).getTime()
    })
    .slice(0, 5)
})

function getTasksForDate(date: Date): Task[] {
  const dateStr = date.toISOString().split('T')[0]
  return taskStore.tasks.filter(task => {
    if (!task.dueDate) return false
    const taskDate = new Date(task.dueDate).toISOString().split('T')[0]
    return taskDate === dateStr
  })
}

function getTaskPriorityClass(priority: TaskPriority): string {
  switch (priority) {
    case 'HIGH':
      return 'task-dot-high'
    case 'MEDIUM':
      return 'task-dot-medium'
    case 'LOW':
      return 'task-dot-low'
    default:
      return ''
  }
}

function previousMonth() {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() - 1, 1)
}

function nextMonth() {
  currentDate.value = new Date(currentDate.value.getFullYear(), currentDate.value.getMonth() + 1, 1)
}

function goToToday() {
  currentDate.value = new Date()
}

onMounted(async () => {
  if (taskStore.tasks.length === 0) {
    await taskStore.fetchTasks()
  }
})
</script>

<style scoped>
.calendar-view {
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

.header-actions {
  display: flex;
  gap: var(--spacing-sm);
}

.calendar-container {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-md);
  margin-bottom: var(--spacing-xl);
}

.calendar-header {
  margin-bottom: var(--spacing-lg);
  text-align: center;
}

.calendar-header h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--gray-900);
  margin: 0;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: var(--spacing-xs);
}

.calendar-day-header {
  text-align: center;
  font-weight: 600;
  color: var(--gray-600);
  padding: var(--spacing-sm);
  font-size: 0.875rem;
}

.calendar-day {
  aspect-ratio: 1;
  border: 1px solid var(--gray-200);
  border-radius: var(--radius-md);
  padding: var(--spacing-xs);
  display: flex;
  flex-direction: column;
  min-height: 80px;
}

.calendar-day.other-month {
  opacity: 0.4;
  background-color: var(--gray-50);
}

.calendar-day.today {
  background-color: var(--primary-lighter);
  border-color: var(--primary-color);
  border-width: 2px;
}

.day-number {
  font-weight: 600;
  font-size: 0.875rem;
  margin-bottom: var(--spacing-xs);
}

.day-tasks {
  display: flex;
  flex-wrap: wrap;
  gap: 2px;
  flex: 1;
  align-content: flex-start;
}

.task-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  flex-shrink: 0;
}

.task-dot-high {
  background-color: var(--danger-color);
}

.task-dot-medium {
  background-color: var(--warning-color);
}

.task-dot-low {
  background-color: var(--success-color);
}

.tasks-list-section {
  margin-top: var(--spacing-xl);
}

.tasks-list-section h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--gray-900);
  margin-bottom: var(--spacing-md);
}

.tasks-list {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
}

.empty-state {
  text-align: center;
  padding: var(--spacing-xl);
  color: var(--gray-600);
  background: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
}

@media (max-width: 768px) {
  .calendar-day {
    min-height: 60px;
    font-size: 0.75rem;
  }

  .task-dot {
    width: 6px;
    height: 6px;
  }
}
</style>

