package com.society.management.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyUpdateRequestDto {

    @NotNull
    private Integer floorNumber;

    @NotNull
    @Positive
    private Integer areaSqft;
}
