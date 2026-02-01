package com.society.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.society.management.enumtype.MaintenanceStatus;

public class MaintenanceResponseDto {

    private final Long maintenanceId;
    private final Long propertyId;
    private final BigDecimal amount;
    private final LocalDate dueDate;
    private final MaintenanceStatus status;

    public MaintenanceResponseDto(
            Long maintenanceId,
            Long propertyId,
            BigDecimal amount,
            LocalDate dueDate,
            MaintenanceStatus status
    ) {
        this.maintenanceId = maintenanceId;
        this.propertyId = propertyId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Long getMaintenanceId() {
        return maintenanceId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }
}
