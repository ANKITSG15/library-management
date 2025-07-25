package com.lms.exception;

import com.lms.dto.GlobalErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GlobalErrorResponseDto> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(GlobalErrorResponseDto.builder().errorMessage(ex.getMessage()).timestamp(LocalDateTime.now()).build());

    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GlobalErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(GlobalErrorResponseDto.builder().errorMessage(ex.getMessage()).timestamp(LocalDateTime.now().now()).build());

    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GlobalErrorResponseDto> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GlobalErrorResponseDto.builder().errorMessage(ex.getMessage()).timestamp(LocalDateTime.now()).build());

    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<GlobalErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GlobalErrorResponseDto.builder().errorMessage(ex.getMessage()).timestamp(LocalDateTime.now()).build());

    }
}
