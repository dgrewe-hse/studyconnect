<template>
  <div class="create-group-view" data-testid="create-group-view">
    <div class="view-header">
      <h1 data-testid="create-group-title">Create Study Group</h1>
      <router-link to="/groups" class="btn btn-secondary" data-testid="btn-cancel">
        ← Back to Groups
      </router-link>
    </div>

    <div class="info-message" data-testid="info-message">
      <p>Group creation feature is coming soon. The backend API for groups is being developed.</p>
    </div>
  </div>
</template>

<script setup lang="ts">
// Create group view - placeholder for future implementation
</script>

<style scoped>
.create-group-view {
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

.info-message {
  background: #dbeafe;
  border-left: 4px solid var(--primary-color);
  padding: var(--spacing-md);
  border-radius: var(--radius-md);
  margin-bottom: var(--spacing-xl);
}

.info-message p {
  color: var(--gray-700);
  margin: 0;
}
</style>

