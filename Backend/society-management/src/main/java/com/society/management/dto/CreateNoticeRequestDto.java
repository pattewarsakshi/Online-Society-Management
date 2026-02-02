package com.society.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoticeRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String message;
}
