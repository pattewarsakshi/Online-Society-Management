package com.society.service;

import com.society.dto.NoticeRequestDto;
import com.society.dto.NoticeResponseDto;
import com.society.entity.Notice;
import com.society.entity.User;
import com.society.repository.NoticeRepository;
import com.society.util.LoggedInUserUtil;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    // ================= CREATE NOTICE =================
    @Transactional
    public NoticeResponseDto createNotice(
            NoticeRequestDto dto,
            HttpSession session) {

        User user = loggedInUserUtil.getUser(session);

        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setDescription(dto.getDescription());
        notice.setPostedBy(user);
        notice.setEmailSent(false);

        Notice saved = noticeRepository.save(notice);
        return map(saved);
    }

    // ================= GET NOTICE (FIXED) =================
    @Transactional(readOnly = true)
    public List<NoticeResponseDto> getNotices(HttpSession session) {

        User user = loggedInUserUtil.getUser(session);
        Integer societyId = user.getSociety().getSocietyId();

        return noticeRepository
                .findByPostedBy_Society_SocietyIdOrderByCreatedAtDesc(societyId)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    // ================= DTO MAPPER =================
    private NoticeResponseDto map(Notice notice) {
        return new NoticeResponseDto(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getDescription(),
                notice.getPostedBy().getFirstName(),
                notice.getCreatedAt()
        );
    }
}
