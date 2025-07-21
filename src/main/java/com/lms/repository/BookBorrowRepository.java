package com.lms.repository;

import com.lms.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface BookBorrowRepository extends JpaRepository<BorrowRecord, Long> {

    @Query(value = "SELECT b from lib_borrow_record_table b where b.due_date < :today and b.return_date is null", nativeQuery = true)
    List<BorrowRecord> findOverdueBooks(@Param("today") LocalDate today);
}
