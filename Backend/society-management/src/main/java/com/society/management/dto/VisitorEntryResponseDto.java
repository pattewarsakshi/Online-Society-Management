package com.society.management.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class VisitorEntryResponseDto {

    private Long visitorEntryId;
    private String visitorName;
    private String visitorPhone;
    private String purpose;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
