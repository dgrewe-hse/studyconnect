package de.hse.swt.studyconnect.repository;

import de.hse.swt.studyconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for accessing {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}

