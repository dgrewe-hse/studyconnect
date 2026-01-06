/**
 * Vue Router configuration
 */

import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginView.vue'),
    meta: { title: 'Sign In' }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layouts/MainLayout.vue'),
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/views/DashboardView.vue'),
        meta: { title: 'Dashboard' }
      },
      {
        path: 'tasks',
        name: 'Tasks',
        component: () => import('@/views/TasksView.vue'),
        meta: { title: 'My Tasks' }
      },
      {
        path: 'tasks/create',
        name: 'CreateTask',
        component: () => import('@/views/CreateTaskView.vue'),
        meta: { title: 'Create Task' }
      },
      {
        path: 'tasks/:id',
        name: 'TaskDetail',
        component: () => import('@/views/TaskDetailView.vue'),
        meta: { title: 'Task Details' }
      },
      {
        path: 'tasks/:id/edit',
        name: 'EditTask',
        component: () => import('@/views/EditTaskView.vue'),
        meta: { title: 'Edit Task' }
      },
      {
        path: 'groups',
        name: 'Groups',
        component: () => import('@/views/GroupsView.vue'),
        meta: { title: 'Study Groups' }
      },
      {
        path: 'groups/create',
        name: 'CreateGroup',
        component: () => import('@/views/CreateGroupView.vue'),
        meta: { title: 'Create Group' }
      },
      {
        path: 'groups/:id',
        name: 'GroupDetail',
        component: () => import('@/views/GroupDetailView.vue'),
        meta: { title: 'Group Details' }
      },
      {
        path: 'calendar',
        name: 'Calendar',
        component: () => import('@/views/CalendarView.vue'),
        meta: { title: 'Calendar' }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/ProfileView.vue'),
        meta: { title: 'Profile' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Authentication guard and title update
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated')
  const title = to.meta.title as string

  // Update document title
  if (title) {
    document.title = `${title} - StudyConnect`
  } else {
    document.title = 'StudyConnect'
  }

  // Allow access to login page
  if (to.name === 'Login') {
    next()
    return
  }

  // Redirect to login if not authenticated
  if (!isAuthenticated) {
    next({ name: 'Login' })
    return
  }

  next()
})

export default router

