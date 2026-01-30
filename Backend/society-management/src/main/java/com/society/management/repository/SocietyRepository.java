package com.society.management.repository;

import com.society.management.entity.Society;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository layer for Society entity.
 * Handles database operations only.
 */
public interface SocietyRepository extends JpaRepository<Society, Long> {

    /**
     * Used to check duplicate society by name
     */
    Optional<Society> findByName(String name);
    
}