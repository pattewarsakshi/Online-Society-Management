package com.society.management.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuardRegisterRequestDto {

    @NotBlank
    private String fullName;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String password;
}
