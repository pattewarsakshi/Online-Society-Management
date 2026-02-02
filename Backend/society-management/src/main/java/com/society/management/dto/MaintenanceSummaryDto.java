package com.society.management.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MaintenanceSummaryDto {

    private Long totalCount;

    private Long pendingCount;
    private Long overdueCount;
    private Long paidCount;

    private BigDecimal pendingAmount;
    private BigDecimal overdueAmount;
    private BigDecimal paidAmount;
}
