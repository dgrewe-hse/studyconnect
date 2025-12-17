package de.hse.swt.studyconnect.repository;

import de.hse.swt.studyconnect.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for accessing {@link Comment} entities.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds all comments for a given task ordered by creation time.
     *
     * @param taskId the task identifier
     * @return list of comments
     */
    List<Comment> findByTask_IdOrderByCreatedAtAsc(Long taskId);
}

