package com.society.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MaintenanceRequestDTO {
    private Integer flatId;
    private Double amount;
    private LocalDate dueDate;
    private LocalDate overDueDate;
}
