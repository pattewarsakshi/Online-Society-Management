package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

    // Resident → view own complaints
    List<Complaint> findByUserUserId(Integer userId);

    // Admin → search by society + tower + flat
    List<Complaint>
    findByFlatSocietySocietyIdAndFlatTowerNameAndFlatFlatNumber(
            Integer societyId,
            String towerName,
            String flatNumber
    );
}
/*
 * Admin sees complaints:
 * - only of their society
 * - filtered by tower and flat
 *
 * NOTE:
 * societyId is NEVER sent from UI
 * It is derived from logged-in admin
 */