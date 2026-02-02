package com.society.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Notice;
import com.society.management.enumtype.NoticeStatus;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findBySociety_SocietyIdAndStatusOrderByCreatedAtDesc(
            Long societyId,
            NoticeStatus status
    );
}
