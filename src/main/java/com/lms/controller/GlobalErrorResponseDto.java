package com.lms.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalErrorResponseDto {

    private String errorMessage;
    private LocalDateTime timestamp;

}
