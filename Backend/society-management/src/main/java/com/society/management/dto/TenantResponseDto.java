package com.society.management.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TenantResponseDto {
	
	
	private Long propertyId;
    private String flatNumber;
    private String block;

    private Long tenantUserId;
    private String tenantName;
    private String tenantEmail;

    private String propertyStatus;}


