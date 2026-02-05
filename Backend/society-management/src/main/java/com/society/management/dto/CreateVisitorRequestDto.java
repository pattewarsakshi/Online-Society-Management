package com.society.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVisitorRequestDto {

    private String name;
    private String phone;
    private String purpose;
    private Long propertyId; // optional
}
