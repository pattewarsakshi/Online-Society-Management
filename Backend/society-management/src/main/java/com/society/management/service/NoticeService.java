package com.society.management.service;

import java.util.List;

import com.society.management.dto.CreateNoticeRequestDto;
import com.society.management.dto.NoticeResponseDto;

public interface NoticeService {

    NoticeResponseDto createNotice(
            Long societyId,
            CreateNoticeRequestDto dto
    );

    List<NoticeResponseDto> getActiveNotices(Long societyId);
}
