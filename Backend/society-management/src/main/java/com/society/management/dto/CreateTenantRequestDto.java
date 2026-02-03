package com.society.management.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTenantRequestDto {
	private Long propertyId;

    // tenant user data
    private String fullName;
    private String email;
    private String phone;
    private String password;
}


