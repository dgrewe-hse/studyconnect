/**
 * Pinia store for task management
 */

import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { taskApi } from '@/services/api'
import type { Task, TaskCreateRequest, TaskUpdateRequest, TaskStatus, TaskPriority } from '@/types'

export const useTaskStore = defineStore('tasks', () => {
  // State
  const tasks = ref<Task[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)
  const currentPage = ref(0)
  const totalPages = ref(0)
  const totalElements = ref(0)

  // Getters
  const personalTasks = computed(() => 
    tasks.value.filter(task => !task.group)
  )

  const groupTasks = computed(() => 
    tasks.value.filter(task => task.group)
  )

  const tasksByStatus = computed(() => {
    return {
      open: tasks.value.filter(t => t.status === 'OPEN'),
      inProgress: tasks.value.filter(t => t.status === 'IN_PROGRESS'),
      completed: tasks.value.filter(t => t.status === 'COMPLETED')
    }
  })

  const tasksByPriority = computed(() => {
    return {
      high: tasks.value.filter(t => t.priority === 'HIGH'),
      medium: tasks.value.filter(t => t.priority === 'MEDIUM'),
      low: tasks.value.filter(t => t.priority === 'LOW')
    }
  })

  const overdueTasks = computed(() => {
    const now = new Date()
    return tasks.value.filter(task => {
      if (!task.dueDate || task.status === 'COMPLETED') return false
      return new Date(task.dueDate) < now
    })
  })

  const dueTodayTasks = computed(() => {
    const today = new Date()
    today.setHours(0, 0, 0, 0)
    const tomorrow = new Date(today)
    tomorrow.setDate(tomorrow.getDate() + 1)
    
    return tasks.value.filter(task => {
      if (!task.dueDate || task.status === 'COMPLETED') return false
      const dueDate = new Date(task.dueDate)
      return dueDate >= today && dueDate < tomorrow
    })
  })

  const dueThisWeekTasks = computed(() => {
    const today = new Date()
    today.setHours(0, 0, 0, 0)
    const nextWeek = new Date(today)
    nextWeek.setDate(nextWeek.getDate() + 7)
    
    return tasks.value.filter(task => {
      if (!task.dueDate || task.status === 'COMPLETED') return false
      const dueDate = new Date(task.dueDate)
      return dueDate >= today && dueDate < nextWeek
    })
  })

  // Actions
  async function fetchTasks(page = 0, size = 20) {
    loading.value = true
    error.value = null
    try {
      const response = await taskApi.getTasks(page, size)
      tasks.value = response.content
      currentPage.value = response.number
      totalPages.value = response.totalPages
      totalElements.value = response.totalElements
    } catch (err: any) {
      error.value = err.message || 'Failed to fetch tasks'
      console.error('Error fetching tasks:', err)
    } finally {
      loading.value = false
    }
  }

  async function fetchTask(id: number) {
    loading.value = true
    error.value = null
    try {
      const task = await taskApi.getTask(id)
      // Update task in list if it exists
      const index = tasks.value.findIndex(t => t.id === id)
      if (index !== -1) {
        tasks.value[index] = task
      } else {
        tasks.value.push(task)
      }
      return task
    } catch (err: any) {
      error.value = err.message || 'Failed to fetch task'
      console.error('Error fetching task:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  async function createTask(taskData: TaskCreateRequest) {
    loading.value = true
    error.value = null
    try {
      const newTask = await taskApi.createTask(taskData)
      tasks.value.unshift(newTask)
      totalElements.value++
      return newTask
    } catch (err: any) {
      error.value = err.message || 'Failed to create task'
      console.error('Error creating task:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  async function updateTask(id: number, taskData: TaskUpdateRequest) {
    loading.value = true
    error.value = null
    try {
      const updatedTask = await taskApi.updateTask(id, taskData)
      const index = tasks.value.findIndex(t => t.id === id)
      if (index !== -1) {
        tasks.value[index] = updatedTask
      }
      return updatedTask
    } catch (err: any) {
      error.value = err.message || 'Failed to update task'
      console.error('Error updating task:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  async function updateTaskStatus(id: number, status: TaskStatus) {
    loading.value = true
    error.value = null
    try {
      const updatedTask = await taskApi.updateTaskStatus(id, status)
      const index = tasks.value.findIndex(t => t.id === id)
      if (index !== -1) {
        tasks.value[index] = updatedTask
      }
      return updatedTask
    } catch (err: any) {
      error.value = err.message || 'Failed to update task status'
      console.error('Error updating task status:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  async function deleteTask(id: number) {
    loading.value = true
    error.value = null
    try {
      await taskApi.deleteTask(id)
      tasks.value = tasks.value.filter(t => t.id !== id)
      totalElements.value--
    } catch (err: any) {
      error.value = err.message || 'Failed to delete task'
      console.error('Error deleting task:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  function clearError() {
    error.value = null
  }

  return {
    // State
    tasks,
    loading,
    error,
    currentPage,
    totalPages,
    totalElements,
    // Getters
    personalTasks,
    groupTasks,
    tasksByStatus,
    tasksByPriority,
    overdueTasks,
    dueTodayTasks,
    dueThisWeekTasks,
    // Actions
    fetchTasks,
    fetchTask,
    createTask,
    updateTask,
    updateTaskStatus,
    deleteTask,
    clearError
  }
})

