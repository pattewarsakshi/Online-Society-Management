package com.society.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminParkingResponseDTO {

    private Integer parkingId;
    private String slotNumber;
    private String vehicleType;
    private String parkingType;
    private String status;
}