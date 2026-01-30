package com.society.management.service;

import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;

/**
 * Society service defines business operations related to society.
 */

public interface SocietyService {
	
	SocietyResponseDto createSociety(SocietyRequestDto requestDto);

}
