package com.lms.controller;

import com.lms.dto.BorrowResponseDto;
import com.lms.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping("/{bookId}/member/{memberId}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<BorrowResponseDto> borrow(@PathVariable Long bookId, @PathVariable Long memberId) {
        return ResponseEntity.ok().body(borrowService.borrowBook(bookId, memberId));
    }

    @PostMapping("/return/{borrowId}")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public ResponseEntity<BorrowResponseDto> returnBook(@PathVariable Long borrowId) {
        return ResponseEntity.ok().body(borrowService.returnBook(borrowId));
    }
}
