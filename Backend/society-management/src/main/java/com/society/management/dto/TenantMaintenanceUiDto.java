package com.society.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TenantMaintenanceUiDto {

    private Long maintenanceId;

    private Integer month;
    private Integer year;

    private BigDecimal amount;
    private String status;

    private LocalDate dueDate;
    private LocalDate paidDate;

    private String urgency; // OVERDUE | DUE_SOON | OK
}
