/**
 * TypeScript type definitions for StudyConnect application
 */

export enum TaskPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

export enum TaskStatus {
  OPEN = 'OPEN',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED'
}

export enum GroupVisibility {
  PRIVATE = 'PRIVATE',
  PUBLIC = 'PUBLIC'
}

export enum GroupRole {
  MEMBER = 'MEMBER',
  ADMIN = 'ADMIN'
}

export interface UserSummary {
  id: number
  name: string
}

export interface GroupSummary {
  id: number
  name: string
  description?: string
  visibility: GroupVisibility
}

export interface Task {
  id: number
  title: string
  description?: string
  dueDate?: string
  priority: TaskPriority
  status: TaskStatus
  category?: string
  createdBy?: UserSummary
  assignedTo?: UserSummary
  group?: GroupSummary
  createdAt: string
  updatedAt: string
}

export interface TaskCreateRequest {
  title: string
  description?: string
  dueDate?: string
  priority: TaskPriority
  category?: string
  groupId?: number
  assignedToId?: number
}

export interface TaskUpdateRequest {
  title?: string
  description?: string
  dueDate?: string
  priority?: TaskPriority
  status?: TaskStatus
  category?: string
  assignedToId?: number
}

export interface Group {
  id: number
  name: string
  description?: string
  visibility: GroupVisibility
  maxMembers: number
  createdBy: UserSummary
  createdAt: string
  updatedAt: string
}

export interface GroupCreateRequest {
  name: string
  description?: string
  visibility: GroupVisibility
  maxMembers?: number
}

export interface Page<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
  first: boolean
  last: boolean
}

