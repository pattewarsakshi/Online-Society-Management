package com.society.management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateRequestDto {

    private String fullName;
    private String email;
    private String phone;
    private String password;
}
