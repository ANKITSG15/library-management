package com.lms.notification;

import com.lms.entity.BorrowRecord;
import com.lms.entity.NotificationLog;
import com.lms.repository.BookBorrowRepository;
import com.lms.repository.NotificationLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class CronScheduler {
    @Autowired
    private BookBorrowRepository bookBorrowRepository;
    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendRemindersOnDuePassDate() {
        List<BorrowRecord> dueSoonRecords = bookBorrowRepository.findOverdueBooks(LocalDate.now());
        for (BorrowRecord dueSoonRecord : dueSoonRecords) {
            String recipientEmail = dueSoonRecord.getMember().getUser().getEmail();
            boolean alreadySent = notificationLogRepository.existsByBorrowRecordAndNotificationType(dueSoonRecord, "REMINDER");
            if (!alreadySent) {
                sendEmail(recipientEmail);
            }
            notificationLogRepository.save(NotificationLog.builder()
                    .borrowRecord(dueSoonRecord)
                    .notificationDate(LocalDateTime.now())
                    .notificationType("REMINDER").build());
        }
    }

    private void sendEmail(String email) {
        log.info("Send notification to email: " + email);
    }
}
