package com.lms.repository;

import com.lms.entity.TrackBook;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookStatusRepository extends JpaRepository<TrackBook, Long> {
    TrackBook findFirst1ByBookIdOrderByUpdatedAtDesc(Long bookId);
}
