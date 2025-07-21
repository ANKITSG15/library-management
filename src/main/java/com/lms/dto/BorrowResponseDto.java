package com.lms.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowResponseDto {
    private Long bookId;
    private String message;
    private Long borrowId;
    private LocalDateTime returnDate;
    private Boolean isBookIssued;
    private Boolean isBookCheckedOut;
}
