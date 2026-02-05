package com.society.management.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VisitorResponseDto {

    private Long visitorId;
    private String name;
    private String phone;
    private String purpose;

    private String flatNumber;
    private String block;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
