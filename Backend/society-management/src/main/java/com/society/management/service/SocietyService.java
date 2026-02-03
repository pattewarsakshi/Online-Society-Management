package com.society.management.service;

import java.util.List;

import com.society.management.dto.AdminCreateRequestDto;
import com.society.management.dto.AdminResponseDto;
import com.society.management.dto.OwnerRegisterRequestDto;
import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;
import com.society.management.dto.UserRegisterRequestDto;


/**
 * Business layer for Society
 */
public interface SocietyService {

    /**
     * Create a new society
     * Only SUPER_ADMIN can do this
     */
	SocietyResponseDto createSociety(SocietyRequestDto requestDto);
    List<SocietyResponseDto> getMySocieties();
    AdminResponseDto createAdmin(Long societyId, AdminCreateRequestDto request);
    void createOwner(Long societyId, OwnerRegisterRequestDto request);
    SocietyResponseDto getSocietyById(Long societyId);
    void createTenant(Long societyId, UserRegisterRequestDto request);



}

