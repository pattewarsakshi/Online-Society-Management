package com.society.management.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO used while creating or updating a society.
 * Contains input validation rules.
 */
@Getter
@Setter
public class SocietyRequestDto {

    @NotBlank(message = "Society name is required")
    @Size(min = 3, max = 100, message = "Society name must be 3–100 characters")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
    private String pincode;
}
