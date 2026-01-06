<template>
  <div class="group-detail-view" data-testid="group-detail-view">
    <div class="info-message" data-testid="info-message">
      <p>Group detail view is coming soon. The backend API for groups is being developed.</p>
      <router-link to="/groups" class="btn btn-secondary" data-testid="btn-back">
        ← Back to Groups
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
// Group detail view - placeholder for future implementation
</script>

<style scoped>
.group-detail-view {
  max-width: 900px;
  margin: 0 auto;
}

.info-message {
  background: #dbeafe;
  border-left: 4px solid var(--primary-color);
  padding: var(--spacing-lg);
  border-radius: var(--radius-md);
  text-align: center;
}

.info-message p {
  color: var(--gray-700);
  margin-bottom: var(--spacing-md);
}
</style>

