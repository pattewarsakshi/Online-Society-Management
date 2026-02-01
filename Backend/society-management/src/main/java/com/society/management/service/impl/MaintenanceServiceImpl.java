package com.society.management.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;
import com.society.management.entity.Maintenance;
import com.society.management.entity.Property;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.enumtype.MaintenanceStatus;
import com.society.management.enumtype.PropertyStatus;
import com.society.management.repository.MaintenanceRepository;
import com.society.management.repository.PropertyRepository;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.MaintenanceService;
import com.society.management.exception.GlobalExceptionHandler;
import com.society.management.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final SocietyRepository societyRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

   //============================================================
    // creation of maintenance by our beloved admin
    @Override
    @Transactional
    public MaintenanceResponseDto createMaintenanceForSociety(
            Long societyId,
            CreateMaintenanceRequestDto dto
    ) {
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        List<Property> properties = propertyRepository
                .findBySociety_SocietyIdAndStatusNot(
                        societyId,
                        PropertyStatus.DELETED
                );

        if (properties.isEmpty()) {
            throw new RuntimeException("No active properties in society");
        }

        List<Maintenance> created = new ArrayList<>();

        for (Property property : properties) {

            boolean exists = maintenanceRepository
                    .findByProperty_PropertyIdAndPeriodMonthAndPeriodYear(
                            property.getPropertyId(),
                            dto.getPeriodMonth(),
                            dto.getPeriodYear()
                    )
                    .isPresent();

            if (exists) {
                continue; // skip duplicate
            }

            Maintenance maintenance = Maintenance.builder()
                    .society(society)
                    .property(property)
                    .periodMonth(dto.getPeriodMonth())
                    .periodYear(dto.getPeriodYear())
                    .amount(dto.getAmount())
                    .status(MaintenanceStatus.PENDING)
                    .generatedAt(LocalDateTime.now())
                    .dueDate(dto.getDueDate())
                    .build();

            created.add(maintenance);
        }

        if (created.isEmpty()) {
            throw new RuntimeException("Maintenance already generated for this period");
        }

        maintenanceRepository.saveAll(created);

        // Return ONE record just to confirm success
        return mapToDto(created.get(0));
    }

    
    //=========================================================================

    @Override
    public List<MaintenanceResponseDto> getBySociety(Long societyId) {

        return maintenanceRepository.findBySociety_SocietyId(societyId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    //=================================================================

    @Override
    public List<MaintenanceResponseDto> getMaintenanceForOwner(
            Long societyId,
            String ownerEmail
    ) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        List<Property> properties = propertyRepository
                .findByOwner_UserIdAndStatusNot(
                        owner.getUserId(),
                        PropertyStatus.DELETED
                );

        if (properties.isEmpty()) {
            return List.of();
        }

        List<Long> propertyIds = properties.stream()
                .map(Property::getPropertyId)
                .toList();

        return maintenanceRepository.findByProperty_PropertyIdIn(propertyIds)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
//================================================================
    @Override
    @Transactional
    public void markAsPaid(Long societyId, Long maintenanceId) {

        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        if (!maintenance.getSociety().getSocietyId().equals(societyId)) {
            throw new RuntimeException("Invalid society maintenance");
        }

        if (maintenance.getStatus() == MaintenanceStatus.PAID) {
            throw new RuntimeException("Maintenance already paid");
        }

        maintenance.setStatus(MaintenanceStatus.PAID);
        maintenance.setPaidAt(LocalDateTime.now());

        maintenanceRepository.save(maintenance);
    }
//==================================================================
    private MaintenanceResponseDto mapToDto(Maintenance m) {
        return MaintenanceResponseDto.builder()
                .maintenanceId(m.getMaintenanceId())
                .propertyId(m.getProperty().getPropertyId())
                .flatNumber(m.getProperty().getFlatNumber())
                .block(m.getProperty().getBlock())
                .periodMonth(m.getPeriodMonth())
                .periodYear(m.getPeriodYear())
                .amount(m.getAmount())
                .status(m.getStatus().name())
                .dueDate(m.getDueDate())
                .paidAt(m.getPaidAt())
                .build();
    }

}

