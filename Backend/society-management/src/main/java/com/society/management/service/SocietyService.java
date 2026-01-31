package com.society.management.service;

import java.util.List;

import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;

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
}

