package com.society.management.service;

import com.society.management.dto.VisitorEntryRequestDto;
import com.society.management.dto.VisitorEntryResponseDto;

public interface VisitorEntryService {

    VisitorEntryResponseDto createEntry(
            Long societyId,
            VisitorEntryRequestDto request,
            String guardEmail
    );

    void markExit(Long visitorEntryId);
}
