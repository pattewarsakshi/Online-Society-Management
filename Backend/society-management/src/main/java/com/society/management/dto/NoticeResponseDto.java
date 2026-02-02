package com.society.management.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NoticeResponseDto {

    private Long noticeId;
    private String title;
    private String message;
    private String status;
    private LocalDateTime createdAt;
}
