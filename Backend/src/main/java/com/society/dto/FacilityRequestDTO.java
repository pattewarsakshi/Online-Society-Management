package com.society.dto;

import com.society.entityenum.FacilityName;
import com.society.entityenum.FacilityStatus;

import lombok.Data;

@Data
public class FacilityRequestDTO {
    private FacilityName facilityName;
    private Integer capacity;
    private Boolean bookingRequired;
    private FacilityStatus status;
}
