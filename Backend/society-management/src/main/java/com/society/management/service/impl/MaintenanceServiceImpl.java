package com.society.management.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;
import com.society.management.dto.MaintenanceSummaryDto;
import com.society.management.dto.OwnerDashboardResponseDto;
import com.society.management.dto.OwnerPropertyMaintenanceDto;
import com.society.management.dto.TenantDashboardResponseDto;
import com.society.management.dto.TenantMaintenanceUiDto;
import com.society.management.entity.Maintenance;
import com.society.management.entity.Property;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.enumtype.MaintenanceStatus;
import com.society.management.enumtype.PropertyStatus;
import com.society.management.enumtype.Role;
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

        // 1️⃣ Society validation
        if (!maintenance.getSociety().getSocietyId().equals(societyId)) {
            throw new RuntimeException("Invalid society maintenance");
        }

        // 2️⃣ Status validation
        if (maintenance.getStatus() == MaintenanceStatus.PAID) {
            throw new RuntimeException("Maintenance already paid");
        }

        // 3️⃣ Ownership validation
        Property property = maintenance.getProperty();
        User owner = property.getOwner();

        String loggedInEmail =
                SecurityContextHolder.getContext().getAuthentication().getName();

        if (!owner.getEmail().equals(loggedInEmail)) {
            throw new RuntimeException("You are not allowed to pay this maintenance");
        }

        // 4️⃣ Mark as PAID
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
 //====================================================================
    @Override
    public MaintenanceSummaryDto getMaintenanceSummary(Long societyId) {

        long total = maintenanceRepository.countBySociety_SocietyId(societyId);

        long pending = maintenanceRepository
                .countBySociety_SocietyIdAndStatus(
                        societyId,
                        MaintenanceStatus.PENDING
                );

        long overdue = maintenanceRepository
                .countBySociety_SocietyIdAndStatus(
                        societyId,
                        MaintenanceStatus.OVERDUE
                );

        long paid = maintenanceRepository
                .countBySociety_SocietyIdAndStatus(
                        societyId,
                        MaintenanceStatus.PAID
                );

        BigDecimal pendingAmt =
                maintenanceRepository.sumAmountBySocietyAndStatus(
                        societyId,
                        MaintenanceStatus.PENDING
                );

        BigDecimal overdueAmt =
                maintenanceRepository.sumAmountBySocietyAndStatus(
                        societyId,
                        MaintenanceStatus.OVERDUE
                );

        BigDecimal paidAmt =
                maintenanceRepository.sumAmountBySocietyAndStatus(
                        societyId,
                        MaintenanceStatus.PAID
                );

        return MaintenanceSummaryDto.builder()
                .totalCount(total)
                .pendingCount(pending)
                .overdueCount(overdue)
                .paidCount(paid)
                .pendingAmount(pendingAmt)
                .overdueAmount(overdueAmt)
                .paidAmount(paidAmt)
                .build();
    }
    
    //=======================================================
    @Override
    public List<MaintenanceResponseDto> getMyMaintenance(String tenantEmail) {

        User tenant = userRepository.findByEmail(tenantEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (tenant.getRole() != Role.TENANT) {
            throw new RuntimeException("Access denied");
        }

        return maintenanceRepository
                .findByProperty_Tenant_UserId(tenant.getUserId())
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    //===========================================================
    //helper method
    private MaintenanceResponseDto mapToResponseDto(Maintenance maintenance) {

        Property property = maintenance.getProperty();

        return MaintenanceResponseDto.builder()
                .maintenanceId(maintenance.getMaintenanceId())
                .propertyId(property.getPropertyId())
                .flatNumber(property.getFlatNumber())
                .block(property.getBlock())
                .periodMonth(maintenance.getPeriodMonth())
                .periodYear(maintenance.getPeriodYear())
                .amount(maintenance.getAmount())
                .status(maintenance.getStatus().name())
                .dueDate(maintenance.getDueDate())
                .paidAt(maintenance.getPaidAt())
                .build();
    }
    
    //=============================================================
    @Override
    public TenantDashboardResponseDto getTenantDashboard() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User tenant = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Object result = maintenanceRepository
                .getTenantDashboardSummary(tenant.getUserId());

        Object[] row;

        // 🔑 handle nested Object[] safely
        if (result instanceof Object[] && ((Object[]) result).length > 0
                && ((Object[]) result)[0] instanceof Object[]) {
            row = (Object[]) ((Object[]) result)[0];
        } else {
            row = (Object[]) result;
        }

        BigDecimal totalAmount = (BigDecimal) row[0];
        BigDecimal paidAmount = (BigDecimal) row[1];
        BigDecimal pendingAmount = (BigDecimal) row[2];
        Long totalBills = ((Number) row[3]).longValue();

        return TenantDashboardResponseDto.builder()
                .totalAmount(totalAmount)
                .paidAmount(paidAmount)
                .pendingAmount(pendingAmount)
                .totalBills(totalBills)
                .build();
    }
    
    //================================================================
    @Override
    public OwnerDashboardResponseDto getOwnerDashboard() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("Access denied");
        }

        Object result = maintenanceRepository
                .getOwnerDashboardSummary(owner.getUserId());

        Object[] row;
        if (result instanceof Object[] && ((Object[]) result).length > 0
                && ((Object[]) result)[0] instanceof Object[]) {
            row = (Object[]) ((Object[]) result)[0];
        } else {
            row = (Object[]) result;
        }

        BigDecimal totalAmount = (BigDecimal) row[0];
        BigDecimal paidAmount = (BigDecimal) row[1];
        BigDecimal pendingAmount = (BigDecimal) row[2];
        Long totalBills = ((Number) row[3]).longValue();

        return OwnerDashboardResponseDto.builder()
                .totalAmount(totalAmount)
                .paidAmount(paidAmount)
                .pendingAmount(pendingAmount)
                .totalBills(totalBills)
                .build();
    }
    
    //=====================================================
    @Override
    public List<OwnerPropertyMaintenanceDto> getOwnerPropertyDashboard() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("Access denied");
        }

        List<Object[]> rows =
                maintenanceRepository.getOwnerPropertyWiseSummary(owner.getUserId());

        return rows.stream().map(row -> {

            Long propertyId = ((Number) row[0]).longValue();
            String flatNumber = (String) row[1];
            String block = (String) row[2];
            BigDecimal totalAmount = (BigDecimal) row[3];
            BigDecimal paidAmount = (BigDecimal) row[4];
            BigDecimal pendingAmount = (BigDecimal) row[5];
            Long totalBills = ((Number) row[6]).longValue();

            return OwnerPropertyMaintenanceDto.builder()
                    .propertyId(propertyId)
                    .flatNumber(flatNumber)
                    .block(block)
                    .totalAmount(totalAmount)
                    .paidAmount(paidAmount)
                    .pendingAmount(pendingAmount)
                    .totalBills(totalBills)
                    .build();
        }).toList();
    }

//=====================================================
    @Override
    public List<TenantMaintenanceUiDto> getTenantMaintenanceUi() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        List<Maintenance> list =
                maintenanceRepository.findTenantMaintenance(email);

        LocalDate today = LocalDate.now();

        return list.stream().map(m -> {

            String urgency = "OK";

            if (m.getStatus() == MaintenanceStatus.PENDING) {

                if (m.getDueDate().isBefore(today)) {
                    urgency = "OVERDUE";
                } else if (!m.getDueDate().isAfter(today.plusDays(7))) {
                    urgency = "DUE_SOON";
                }
            }

            return TenantMaintenanceUiDto.builder()
                    .maintenanceId(m.getMaintenanceId())
                    .month(m.getPeriodMonth())
                    .year(m.getPeriodYear())
                    .amount(m.getAmount())
                    .status(m.getStatus().name()) // enum → String for UI
                    .dueDate(m.getDueDate())
                    .paidDate(
                            m.getPaidAt() != null
                                    ? m.getPaidAt().toLocalDate()
                                    : null
                    )
                    .urgency(urgency)
                    .build();
        }).toList();
    }

//=====================================================
    @Override
    @Transactional
    public void payMaintenanceAsTenant(Long maintenanceId, String tenantEmail) {

        Maintenance m = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new RuntimeException("Maintenance not found"));

        if (m.getStatus() == MaintenanceStatus.PAID) {
            throw new RuntimeException("Maintenance already paid");
        }

        // Ensure logged-in tenant owns this maintenance
        if (!m.getProperty().getTenant().getEmail().equals(tenantEmail)) {
            throw new RuntimeException("Unauthorized payment attempt");
        }

        m.setStatus(MaintenanceStatus.PAID);
        m.setPaidAt(LocalDateTime.now());

        maintenanceRepository.save(m);
    }


}

