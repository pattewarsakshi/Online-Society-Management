package com.society.management.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GuardDashboardResponseDto {

    private long totalVisitorsToday;
    private long visitorsInside;
}