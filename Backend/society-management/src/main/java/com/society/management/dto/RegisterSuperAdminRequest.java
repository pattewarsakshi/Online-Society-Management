package com.society.management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterSuperAdminRequest {

    @NotBlank
    @Size(min = 3, max = 100)
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phone;

    @NotBlank
    @Size(min = 6)
    private String password;
}
