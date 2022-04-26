package org.andriipolishchuk.currentexchange.web;

import lombok.Data;
import org.andriipolishchuk.currentexchange.exception.FinageBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({
            FinageBadRequestException.class,
    })
    public ResponseEntity<ExceptionDto> e400(Exception ex) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionDto> handleException(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(ExceptionDto.of(status, ex.getMessage()), status);
    }

    @Data
    private static class ExceptionDto {
        private int code;
        private String message;

        static ExceptionDto of(HttpStatus status, String message) {
            ExceptionDto dto = new ExceptionDto();
            dto.setCode(status.value());
            dto.setMessage(message);
            return dto;
        }
    }
}
