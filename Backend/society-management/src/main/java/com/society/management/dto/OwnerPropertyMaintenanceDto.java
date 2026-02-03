package com.society.management.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OwnerPropertyMaintenanceDto {

    private Long propertyId;
    private String flatNumber;
    private String block;

    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal pendingAmount;
    private Long totalBills;
}
