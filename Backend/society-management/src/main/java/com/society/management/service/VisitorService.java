package com.society.management.service;

import java.util.List;

import com.society.management.dto.CreateVisitorRequestDto;
import com.society.management.dto.VisitorResponseDto;

public interface VisitorService {

    VisitorResponseDto registerVisitor(
            Long societyId,
            CreateVisitorRequestDto dto,
            String guardEmail
    );

    List<VisitorResponseDto> getTodayVisitors(
            Long societyId
    );

    void markExit(Long visitorId, String guardEmail);
}
