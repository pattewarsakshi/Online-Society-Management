package com.society.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.society.dto.MaintenanceRequestDTO;
import com.society.dto.MaintenanceResponseDTO;

public interface MaintenanceService {
	
    MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO dto);

    List<MaintenanceResponseDTO> getAllMaintenance();

    List<MaintenanceResponseDTO> getMaintenanceByFlat(Integer flatId);

    void uploadPaymentProof(Integer maintenanceId,
                            String transactionId,
                            MultipartFile file);

    MaintenanceResponseDTO approvePayment(Integer maintenanceId);

    MaintenanceResponseDTO rejectPayment(Integer maintenanceId);
}
