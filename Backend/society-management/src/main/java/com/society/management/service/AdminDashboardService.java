package com.society.management.service;

import com.society.management.dto.AdminDashboardDto;

public interface AdminDashboardService {
	
	AdminDashboardDto getDashboard(Long societyId);

}
