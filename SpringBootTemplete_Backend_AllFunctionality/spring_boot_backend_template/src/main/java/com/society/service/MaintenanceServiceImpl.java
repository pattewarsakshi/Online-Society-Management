//package com.society.service;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.util.List;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.society.dto.MaintenanceRequestDTO;
//import com.society.dto.MaintenanceResponseDTO;
//import com.society.entity.Flat;
//import com.society.entity.Maintenance;
//import com.society.entityenum.PaymentStatus;
//import com.society.repository.FlatRepository;
//import com.society.repository.MaintenanceRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class MaintenanceServiceImpl implements MaintenanceService {
//
//    private final MaintenanceRepository maintenanceRepository;
//    private final FlatRepository flatRepository;
//    private final ModelMapper modelMapper;
//
//    @Override
//    public MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO dto) {
//
//        Flat flat = flatRepository.findById(dto.getFlatId())
//                .orElseThrow(() -> new RuntimeException("Flat not found"));
//
//        Maintenance maintenance = new Maintenance();
//        maintenance.setFlat(flat);
//        maintenance.setAmount(dto.getAmount());
//        maintenance.setDueDate(dto.getDueDate());
//        maintenance.setOverDueDate(dto.getOverDueDate());
//        maintenance.setPaymentStatus(PaymentStatus.PENDING);
//
//        return mapToDTO(maintenanceRepository.save(maintenance));
//    }
//
//    @Override
//    public List<MaintenanceResponseDTO> getAllMaintenance() {
//        return maintenanceRepository.findAll()
//                .stream()
//                .map(this::mapToDTO)
//                .toList();
//    }
//
//    @Override
//    public List<MaintenanceResponseDTO> getMaintenanceByFlat(Integer flatId) {
//        return maintenanceRepository.findByFlat_FlatId(flatId)
//                .stream()
//                .map(this::mapToDTO)
//                .toList();
//    }
//
//    @Override
//    public void uploadPaymentProof(Integer id,
//                                   String transactionId,
//                                   MultipartFile file) {
//
//        Maintenance maintenance = maintenanceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Maintenance not found"));
//
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//        try {
//            Path uploadPath = Paths.get("uploads");
//            Files.createDirectories(uploadPath);
//            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
//        } catch (Exception e) {
//            throw new RuntimeException("File upload failed");
//        }
//
//        maintenance.setTransactionId(transactionId);
//        maintenance.setPaymentProof(fileName);
//        maintenance.setPaymentStatus(PaymentStatus.APPROVAL_PENDING);
//
//        maintenanceRepository.save(maintenance);
//    }
//
//    @Override
//    public MaintenanceResponseDTO approvePayment(Integer id) {
//
//        Maintenance m = maintenanceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Not found"));
//
//        m.setPaymentStatus(PaymentStatus.PAID);
//        m.setPaymentDate(LocalDate.now());
//
//        return mapToDTO(maintenanceRepository.save(m));
//    }
//
//    @Override
//    public MaintenanceResponseDTO rejectPayment(Integer id) {
//
//        Maintenance m = maintenanceRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Not found"));
//
//        m.setPaymentStatus(PaymentStatus.REJECTED);
//
//        return mapToDTO(maintenanceRepository.save(m));
//    }
//
//    private MaintenanceResponseDTO mapToDTO(Maintenance m) {
//        MaintenanceResponseDTO dto = modelMapper.map(m, MaintenanceResponseDTO.class);
//        dto.setFlatId(m.getFlat().getFlatId());
//        return dto;
//    }
//    
//}
//

package com.society.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.society.dto.MaintenanceRequestDTO;
import com.society.dto.MaintenanceResponseDTO;
import com.society.entity.Flat;
import com.society.entity.Maintenance;
import com.society.entityenum.PaymentStatus;
import com.society.repository.FlatRepository;
import com.society.repository.MaintenanceRepository;
import com.society.util.ParkingChargeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final FlatRepository flatRepository;

    // 🔹 CREATE MAINTENANCE WITH PARKING
    @Override
    public MaintenanceResponseDTO createMaintenance(MaintenanceRequestDTO dto) {

        Flat flat = flatRepository.findById(dto.getFlatId())
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        double baseAmount = dto.getAmount();
        double parkingCharge =
                ParkingChargeUtil.getParkingCharge(flat.getParkingType());
        double totalAmount = baseAmount + parkingCharge;

        Maintenance maintenance = new Maintenance();
        maintenance.setFlat(flat);
        maintenance.setAmount(totalAmount);
        maintenance.setDueDate(dto.getDueDate());
        maintenance.setOverDueDate(dto.getOverDueDate());
        maintenance.setPaymentStatus(PaymentStatus.PENDING);

        return mapToDTO(maintenanceRepository.save(maintenance));
    }

    @Override
    public List<MaintenanceResponseDTO> getAllMaintenance() {
        return maintenanceRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<MaintenanceResponseDTO> getMaintenanceByFlat(Integer flatId) {
        return maintenanceRepository.findByFlat_FlatId(flatId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void uploadPaymentProof(Integer id,
                                   String transactionId,
                                   MultipartFile file) {

        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }

        maintenance.setTransactionId(transactionId);
        maintenance.setPaymentProof(fileName);
        maintenance.setPaymentStatus(PaymentStatus.APPROVAL_PENDING);

        maintenanceRepository.save(maintenance);
    }

    @Override
    public MaintenanceResponseDTO approvePayment(Integer id) {

        Maintenance m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        m.setPaymentStatus(PaymentStatus.PAID);
        m.setPaymentDate(LocalDate.now());

        return mapToDTO(m);
    }

    @Override
    public MaintenanceResponseDTO rejectPayment(Integer id) {

        Maintenance m = maintenanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        m.setPaymentStatus(PaymentStatus.REJECTED);

        return mapToDTO(m);
    }

    // 🔹 RESPONSE MAPPING (BUSINESS LOGIC)
    private MaintenanceResponseDTO mapToDTO(Maintenance m) {

        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();

        double totalAmount = m.getAmount();
        double parkingCharge =
                ParkingChargeUtil.getParkingCharge(
                        m.getFlat().getParkingType());
        double baseAmount = totalAmount - parkingCharge;

        dto.setMaintenanceId(m.getMaintenanceId());
        dto.setFlatId(m.getFlat().getFlatId());
        dto.setBaseAmount(baseAmount);
        dto.setParkingCharge(parkingCharge);
        dto.setTotalAmount(totalAmount);

        dto.setDueDate(m.getDueDate());
        dto.setOverDueDate(m.getOverDueDate());
        dto.setPaymentDate(m.getPaymentDate());
        dto.setPaymentStatus(m.getPaymentStatus());
        dto.setTransactionId(m.getTransactionId());
        dto.setPaymentProof(m.getPaymentProof());

        return dto;
    }
}

