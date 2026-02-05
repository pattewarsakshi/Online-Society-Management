package com.society.management.repository;

import com.society.management.entity.VisitorEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitorEntryRepository extends JpaRepository<VisitorEntry, Long> {

    List<VisitorEntry> findBySociety_SocietyIdAndEntryTimeBetween(
            Long societyId,
            LocalDateTime start,
            LocalDateTime end
    );
    
    long countBySociety_SocietyIdAndEntryTimeBetween(
            Long societyId,
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );

    long countBySociety_SocietyIdAndExitTimeIsNull(Long societyId);

}
