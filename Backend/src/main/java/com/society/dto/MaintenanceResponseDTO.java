package com.society.dto;

import java.time.LocalDate;

import com.society.entityenum.PaymentStatus;

import lombok.Data;

@Data
public class MaintenanceResponseDTO {
    private Integer maintenanceId;
    private Integer flatId;
    private Double amount;
    private LocalDate dueDate;
    private LocalDate overDueDate;
    private LocalDate paymentDate;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String paymentProof;
}
