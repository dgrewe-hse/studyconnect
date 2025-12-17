package de.hse.swt.studyconnect.repository;

import de.hse.swt.studyconnect.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for accessing {@link Group} entities.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
}

