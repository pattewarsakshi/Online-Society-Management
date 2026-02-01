package com.society.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerChangeRequestDto {

    @NotNull
    private Long newOwnerUserId;
}
