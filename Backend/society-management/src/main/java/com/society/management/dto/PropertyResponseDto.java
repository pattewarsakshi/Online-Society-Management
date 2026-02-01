package com.society.management.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyResponseDto {

    private Long propertyId;
    private String flatNumber;
    private String block;
    private Integer floorNumber;
    private Integer areaSqft;
    private String status;

    private Long ownerId;
    private String ownerName;

    private Long tenantId;
    private String tenantName;
}
