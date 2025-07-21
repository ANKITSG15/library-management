package com.lms.notification;

import com.lms.entity.BorrowRecord;
import com.lms.repository.BookBorrowRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class CronScheduler {
    @Autowired
    private BookBorrowRepository bookBorrowRepository;

    @Scheduled(cron = "0 0 8 * * *")
    public void sendRemindersOnDuePassDate() {
        List<BorrowRecord> dueSoonRecords = bookBorrowRepository.findOverdueBooks(LocalDate.now());
        for (BorrowRecord dueSoonRecord : dueSoonRecords) {
            String recipientEmail = dueSoonRecord.getMember().getUser().getEmail();
            sendEmail(recipientEmail);
        }
    }

    private void sendEmail(String email) {
        log.info("Send notification to email: " + email);
    }
}
