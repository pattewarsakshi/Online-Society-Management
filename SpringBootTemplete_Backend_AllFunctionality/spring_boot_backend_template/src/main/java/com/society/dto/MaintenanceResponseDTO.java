package com.society.dto;

import java.time.LocalDate;

import com.society.entityenum.PaymentStatus;

import lombok.Data;

@Data
public class MaintenanceResponseDTO {

    private Integer maintenanceId;
    private Integer flatId;

    // 🔹 Billing breakdown
    private Double baseAmount;
    private Double parkingCharge;
    private Double totalAmount;

    // 🔹 Dates
    private LocalDate dueDate;
    private LocalDate overDueDate;
    private LocalDate paymentDate;

    // 🔹 Payment details
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String paymentProof;
}
