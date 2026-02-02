package com.society.management.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.management.dto.CreateNoticeRequestDto;
import com.society.management.dto.NoticeResponseDto;
import com.society.management.entity.Notice;
import com.society.management.entity.Society;
import com.society.management.enumtype.NoticeStatus;
import com.society.management.repository.NoticeRepository;
import com.society.management.repository.SocietyRepository;
import com.society.management.service.NoticeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final SocietyRepository societyRepository;

    @Override
    @Transactional
    public NoticeResponseDto createNotice(
            Long societyId,
            CreateNoticeRequestDto dto
    ) {
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        Notice notice = Notice.builder()
                .society(society)
                .title(dto.getTitle())
                .message(dto.getMessage())
                .status(NoticeStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        return mapToDto(noticeRepository.save(notice));
    }

    @Override
    public List<NoticeResponseDto> getActiveNotices(Long societyId) {

        return noticeRepository
                .findBySociety_SocietyIdAndStatusOrderByCreatedAtDesc(
                        societyId,
                        NoticeStatus.ACTIVE
                )
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private NoticeResponseDto mapToDto(Notice notice) {
        return NoticeResponseDto.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .message(notice.getMessage())
                .status(notice.getStatus().name())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}

