package com.society.management.repository;

import com.society.management.entity.Society;
import com.society.management.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Society entity
 */
public interface SocietyRepository extends JpaRepository<Society, Long> {

    /**
     * Used to prevent duplicate societies by name
     */
    Optional<Society> findBySocietyName(String societyName);
    
    List<Society> findByCreatedBy(User createdBy);
    
    boolean existsBySocietyName(String societyName);

	List<Society> findByCreatedByUserId(Long userId);
}
