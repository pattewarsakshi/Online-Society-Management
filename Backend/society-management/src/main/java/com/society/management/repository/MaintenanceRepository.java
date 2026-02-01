package com.society.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

	List<Maintenance> findByProperty_PropertyId(Long propertyId);

    List<Maintenance> findByProperty_Society_SocietyId(Long societyId);

    List<Maintenance> findByProperty_Owner_UserId(Long userId);
}
