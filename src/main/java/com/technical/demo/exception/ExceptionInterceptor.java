package com.technical.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A controller advice class that intercepts and handles exceptions thrown by controllers.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionInterceptor {

    /**
     * Handles BadRequestException and returns a ResponseEntity with an ApiError object containing error details.
     *
     * @param ex The BadRequestException that was thrown.
     * @return A ResponseEntity with an ApiError object containing error details.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        log.error("Error occurred with exception {}", ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message("Bad Request")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(error);
    }

    /**
     * Handles NotFoundException and returns a ResponseEntity with an ApiError object containing error details.
     *
     * @param ex The NotFoundException that was thrown.
     * @return A ResponseEntity with an ApiError object containing error details.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        log.error("Error occurred with exception {}", ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message("Not Found")
                .errors(details)
                .build();
        return ResponseEntityBuilder.build(error);
    }

    /**
     * Handles all uncaught RuntimeExceptions and returns a ResponseEntity with an ApiError object containing error details.
     *
     * @param ex The RuntimeException that was thrown.
     * @return A ResponseEntity with an ApiError object containing error details.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleAllUncaughtException(RuntimeException ex) {
        log.error("Error occurred with exception {}", ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Technical Error Please Try Later",
                details);
        return ResponseEntityBuilder.build(error);
    }

}
