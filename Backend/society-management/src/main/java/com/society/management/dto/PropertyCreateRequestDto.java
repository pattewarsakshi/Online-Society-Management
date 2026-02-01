package com.society.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyCreateRequestDto {

    private String flatNumber;
    private String block;
    private Integer floorNumber;
    private Integer areaSqft;
    private Long ownerUserId;
}

