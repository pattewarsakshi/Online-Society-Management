package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.entity.Maintenance;
import com.society.entityenum.PaymentStatus;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer>{
	List<Maintenance> findByFlat_FlatId(Integer flatId);

    List<Maintenance> findByPaymentStatus(PaymentStatus status);
}
