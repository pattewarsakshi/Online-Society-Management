package com.society.management.dto;

import com.society.management.enumtype.Role;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Input DTO for user creation
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    // Society ID comes from client
    private Long societyId;
}
