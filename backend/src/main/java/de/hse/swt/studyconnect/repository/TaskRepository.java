package de.hse.swt.studyconnect.repository;

import de.hse.swt.studyconnect.entity.Task;
import de.hse.swt.studyconnect.entity.User;
import de.hse.swt.studyconnect.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for accessing {@link Task} entities.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Finds tasks created by a specific user.
     *
     * @param createdBy the creator of the tasks
     * @param pageable  paging information
     * @return page of tasks
     */
    Page<Task> findByCreatedBy(User createdBy, Pageable pageable);

    /**
     * Finds group tasks by group id.
     *
     * @param groupId  the group identifier
     * @param pageable paging information
     * @return page of tasks
     */
    Page<Task> findByGroup_Id(Long groupId, Pageable pageable);

    /**
     * Finds tasks for a creator filtered by status.
     *
     * @param createdBy the creator
     * @param status    task status
     * @param pageable  paging information
     * @return page of tasks
     */
    Page<Task> findByCreatedByAndStatus(User createdBy, TaskStatus status, Pageable pageable);
}

