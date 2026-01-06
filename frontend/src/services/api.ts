/**
 * API service for communicating with the StudyConnect backend
 */

import axios, { AxiosInstance, AxiosError } from 'axios'
import type { Task, TaskCreateRequest, TaskUpdateRequest, Page } from '@/types'

// Backend has context-path=/api and controller @RequestMapping("/api/v1/tasks")
// So full path is: /api (context-path) + /api/v1/tasks (controller) = /api/api/v1/tasks
// Base URL should include the context-path
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

/**
 * Get the current user ID from localStorage
 * In a real app, this would come from authentication
 */
function getCurrentUserId(): number {
  const userId = localStorage.getItem('userId')
  if (!userId) {
    // Default user ID for development/testing
    const defaultUserId = 1
    localStorage.setItem('userId', defaultUserId.toString())
    return defaultUserId
  }
  return parseInt(userId, 10)
}

/**
 * Set the current user ID
 */
export function setCurrentUserId(userId: number): void {
  localStorage.setItem('userId', userId.toString())
}

/**
 * Create axios instance with default configuration
 */
const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

/**
 * Request interceptor to add X-User-Id header
 */
apiClient.interceptors.request.use(
  (config) => {
    const userId = getCurrentUserId()
    config.headers['X-User-Id'] = userId.toString()
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

/**
 * Response interceptor for error handling
 */
apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response) {
      // Server responded with error status
      console.error('API Error:', error.response.status, error.response.data)
    } else if (error.request) {
      // Request made but no response received
      console.error('Network Error:', error.request)
    } else {
      // Something else happened
      console.error('Error:', error.message)
    }
    return Promise.reject(error)
  }
)

/**
 * Task API endpoints
 */
export const taskApi = {
  /**
   * Get paginated list of tasks
   */
  async getTasks(page = 0, size = 20): Promise<Page<Task>> {
    // Backend: context-path=/api, controller @RequestMapping("/api/v1/tasks")
    // Full path: /api + /api/v1/tasks = /api/api/v1/tasks
    // Base URL is /api, so endpoint is /api/v1/tasks
    const response = await apiClient.get('/api/v1/tasks', {
      params: { page, size }
    })
    return response.data
  },

  /**
   * Get a single task by ID
   */
  async getTask(id: number): Promise<Task> {
    const response = await apiClient.get(`/api/v1/tasks/${id}`)
    return response.data
  },

  /**
   * Create a new task
   */
  async createTask(task: TaskCreateRequest): Promise<Task> {
    const response = await apiClient.post('/api/v1/tasks', task)
    return response.data
  },

  /**
   * Update an existing task
   */
  async updateTask(id: number, task: TaskUpdateRequest): Promise<Task> {
    const response = await apiClient.put(`/api/v1/tasks/${id}`, task)
    return response.data
  },

  /**
   * Update task status
   */
  async updateTaskStatus(id: number, status: Task['status']): Promise<Task> {
    const response = await apiClient.patch(`/api/v1/tasks/${id}/status`, { status })
    return response.data
  },

  /**
   * Delete a task
   */
  async deleteTask(id: number): Promise<void> {
    await apiClient.delete(`/api/v1/tasks/${id}`)
  }
}

/**
 * Health check API
 */
export const healthApi = {
  async check(): Promise<{ status: string; service: string; timestamp: string; version: string }> {
    const response = await apiClient.get('/health')
    return response.data
  },

  async ping(): Promise<{ message: string; timestamp: string }> {
    const response = await apiClient.get('/health/ping')
    return response.data
  }
}

export default apiClient

