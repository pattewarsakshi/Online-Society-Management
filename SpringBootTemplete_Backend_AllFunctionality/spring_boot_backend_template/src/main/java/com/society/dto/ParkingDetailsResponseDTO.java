package com.society.dto;

import lombok.Data;

@Data
public class ParkingDetailsResponseDTO {

    private Integer flatId;
    private String parkingType;
    private Double parkingCharge;
}
