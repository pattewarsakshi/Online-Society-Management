package com.society.service;

import java.util.List;
import com.society.dto.VisitorEntryDTO;
import com.society.dto.VisitorResponseDTO;

public interface VisitorService {

    VisitorResponseDTO addVisitor(
            Integer societyId,
            Integer userId,
            VisitorEntryDTO dto
    );

    VisitorResponseDTO exitVisitor(
            Integer societyId,
            Integer visitorId
    );

    List<VisitorResponseDTO> getInsideVisitors(Integer societyId);

    List<VisitorResponseDTO> getTodayVisitors(Integer societyId);

    List<VisitorResponseDTO> getAllVisitors(Integer societyId);
}
