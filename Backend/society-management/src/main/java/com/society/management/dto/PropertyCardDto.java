package com.society.management.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyCardDto {

    private Long propertyId;
    private String flatNumber;
    private String block;

    private String ownerName;
    private String tenantName;

    private String status; // OCCUPIED / VACANT
}
