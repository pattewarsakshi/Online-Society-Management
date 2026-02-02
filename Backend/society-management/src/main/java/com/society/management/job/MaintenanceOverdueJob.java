package com.society.management.job;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.society.management.entity.Maintenance;
import com.society.management.enumtype.MaintenanceStatus;
import com.society.management.repository.MaintenanceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MaintenanceOverdueJob {

    private final MaintenanceRepository maintenanceRepository;

    @Scheduled(cron = "0 0 1 * * ?") // every day at 1 AM
    @Transactional
    public void markOverdueMaintenance() {

        List<Maintenance> overdueList =
                maintenanceRepository.findByStatusAndDueDateBefore(
                        MaintenanceStatus.PENDING,
                        LocalDate.now()
                );
        
        System.out.println("Found records: " + overdueList.size());


        if (overdueList.isEmpty()) {
            return;
        }

        for (Maintenance maintenance : overdueList) {
            maintenance.setStatus(MaintenanceStatus.OVERDUE);
        }

        maintenanceRepository.saveAll(overdueList);
    }
}
