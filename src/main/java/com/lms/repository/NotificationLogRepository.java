package com.lms.repository;

import com.lms.entity.BorrowRecord;
import com.lms.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    boolean existsByBorrowRecordAndNotificationType(BorrowRecord borrowRecord, String type);

}
