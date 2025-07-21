package com.lms.service;


import com.lms.dto.BookStatus;
import com.lms.dto.BorrowResponseDto;
import com.lms.entity.Book;
import com.lms.entity.BorrowRecord;
import com.lms.entity.Member;
import com.lms.entity.TrackBook;
import com.lms.repository.BookBorrowRepository;
import com.lms.repository.BookRepository;
import com.lms.repository.BookStatusRepository;
import com.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BorrowService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookStatusRepository bookStatusRepository;
    @Autowired
    private BookBorrowRepository bookBorrowRepository;

    @Transactional
    public BorrowResponseDto borrowBook(Long bookId, Long memberId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Member> memberOpt = memberRepository.findById(memberId);

        if (bookOpt.isEmpty() || memberOpt.isEmpty()) {
            return BorrowResponseDto.builder().isBookIssued(false).message("Invalid book-id or member-id").build();
        }

        Book book = bookOpt.get();
        if (!book.isAvailable()) {
            return BorrowResponseDto.builder().isBookIssued(false).message("Given book is not available currently").build();
        }

        BorrowRecord borrowRecord = BorrowRecord.builder()
                .book(book)
                .member(memberOpt.get())
                .issuedDate(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(15))
                .build();
        BorrowRecord savedBorrowRecord = bookBorrowRepository.save(borrowRecord);

        book.setAvailable(false);
        book = bookRepository.save(book);

        TrackBook trackBook = bookStatusRepository.save(TrackBook.builder().bookStatus(BookStatus.BORROWED).book(book).build());
        return BorrowResponseDto.builder()
                .isBookIssued(true)
                .message("Book issues successfully")
                .bookId(bookId)
                .returnDate(savedBorrowRecord.getReturnDate())
                .borrowId(trackBook.getId())
                .build();
    }

    @Transactional
    public BorrowResponseDto returnBook(Long trackingId) {
        Optional<BorrowRecord> trackingRecord = bookBorrowRepository.findById(trackingId);

        if (trackingRecord.isEmpty()) {
            return BorrowResponseDto.builder().isBookCheckedOut(false).message("Invalid borrow-id or tracking-id").build();
        }

        BorrowRecord borrowRecord = trackingRecord.get();
        borrowRecord.setReturnDate(LocalDateTime.now());
        bookBorrowRepository.save(borrowRecord);

        Book book = borrowRecord.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        TrackBook latestBookTrackRecord = bookStatusRepository.findFirst1ByBookIdOrderByUpdatedAtDesc(book.getId());
        bookStatusRepository.save(TrackBook.builder().book(book).bookStatus(BookStatus.AVAILABLE).location(latestBookTrackRecord.getLocation()).build());

        return BorrowResponseDto.builder()
                .isBookCheckedOut(true)
                .message("Book returned successfully")
                .borrowId(trackingId)
                .build();
    }
}
