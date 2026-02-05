package com.society.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.society.management.entity.Visitor;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    @Query("""
        SELECT v
        FROM Visitor v
        WHERE v.society.societyId = :societyId
          AND DATE(v.entryTime) = CURRENT_DATE
        ORDER BY v.entryTime DESC
    """)
    List<Visitor> findTodayVisitors(@Param("societyId") Long societyId);
}
