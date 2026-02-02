package com.society.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAmenityRequestDto {

    @NotBlank
    private String name;

    private String description;
}