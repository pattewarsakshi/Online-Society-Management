package com.society.management.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.CreateNoticeRequestDto;
import com.society.management.dto.NoticeResponseDto;
import com.society.management.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies/{societyId}/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // ADMIN creates notice
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public NoticeResponseDto createNotice(
            @PathVariable Long societyId,
            @RequestBody @Valid CreateNoticeRequestDto dto
    ) {
        return noticeService.createNotice(societyId, dto);
    }

    // ALL users read notices
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','OWNER','TENANT')")
    public List<NoticeResponseDto> getNotices(
            @PathVariable Long societyId
    ) {
        return noticeService.getActiveNotices(societyId);
    }
}
