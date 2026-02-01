package com.society.management.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMaintenanceRequestDto {

    @NotNull
    private Integer periodMonth;   // 1–12

    @NotNull
    private Integer periodYear;    // 2025, 2026

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate dueDate;
}

