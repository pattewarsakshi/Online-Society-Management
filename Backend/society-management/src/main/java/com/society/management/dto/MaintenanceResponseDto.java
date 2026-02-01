package com.society.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.society.management.enumtype.MaintenanceStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MaintenanceResponseDto {

    private Long maintenanceId;

    // Property info (for UI)
    private Long propertyId;
    private String flatNumber;
    private String block;

    // Period
    private Integer periodMonth;
    private Integer periodYear;

    private BigDecimal amount;
    private String status;

    private LocalDate dueDate;
    private LocalDateTime paidAt;
}
