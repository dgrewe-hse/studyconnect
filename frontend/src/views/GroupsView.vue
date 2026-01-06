<template>
  <div class="groups-view" data-testid="groups-view">
    <div class="view-header">
      <h1 data-testid="groups-title">Study Groups</h1>
      <router-link to="/groups/create" class="btn btn-primary" data-testid="btn-create-group">
        Create Group
      </router-link>
    </div>

    <div class="info-message" data-testid="info-message">
      <p>Group management features are coming soon. The backend API for groups is being developed.</p>
    </div>

    <div class="placeholder-content">
      <div class="placeholder-card" data-testid="placeholder-card">
        <h3>Groups Feature</h3>
        <p>This section will display:</p>
        <ul>
          <li>List of study groups you're a member of</li>
          <li>Group details and members</li>
          <li>Group tasks and assignments</li>
          <li>Group collaboration features</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// Groups view - placeholder for future implementation
</script>

<style scoped>
.groups-view {
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

.placeholder-content {
  display: flex;
  justify-content: center;
}

.placeholder-card {
  background: white;
  border-radius: var(--radius-lg);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-md);
  max-width: 600px;
}

.placeholder-card h3 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--gray-900);
  margin-bottom: var(--spacing-md);
}

.placeholder-card p {
  color: var(--gray-600);
  margin-bottom: var(--spacing-sm);
}

.placeholder-card ul {
  color: var(--gray-600);
  padding-left: var(--spacing-lg);
}

.placeholder-card li {
  margin-bottom: var(--spacing-xs);
}
</style>

