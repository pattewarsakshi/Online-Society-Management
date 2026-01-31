package com.society.management.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO used when creating a new society
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocietyRequestDto {

    @NotBlank(message = "Society name is required")
    private String societyName;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @Pattern(
        regexp = "^[1-9][0-9]{5}$",
        message = "Invalid Indian pincode"
    )
    private String pincode;
}
