package de.hse.swt.studyconnect.repository;

import de.hse.swt.studyconnect.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing {@link GroupMember} entities.
 */
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    /**
     * Finds all memberships for a given group.
     *
     * @param groupId the group identifier
     * @return list of memberships
     */
    List<GroupMember> findByGroup_Id(Long groupId);

    /**
     * Finds membership for a given group and user.
     *
     * @param groupId the group identifier
     * @param userId  the user identifier
     * @return optional membership
     */
    Optional<GroupMember> findByGroup_IdAndUser_Id(Long groupId, Long userId);
}

