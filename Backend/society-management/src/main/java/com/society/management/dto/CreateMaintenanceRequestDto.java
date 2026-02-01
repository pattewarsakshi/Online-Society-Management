package com.society.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateMaintenanceRequestDto {

    private final BigDecimal amount;
    private final LocalDate dueDate;

    public CreateMaintenanceRequestDto(
            BigDecimal amount,
            LocalDate dueDate
    ) {
        this.amount = amount;
        this.dueDate = dueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
