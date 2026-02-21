package com.mith.movie_booking_platform.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author mithl
 * @date 20-02-2026
 * @email mithleshshah84@gmail.com
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 404, "Resource Not Found",ex.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(SeatNotAvailableException.class)
    public ResponseEntity<ErrorResponse> seatNotAvailableExceptionHandler(SeatNotAvailableException ex){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 404, "Seat Not Available",ex.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericExceptionHandler(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), 404, "Internal Server Error",ex.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }


}

@AllArgsConstructor
@Data
class ErrorResponse{
    private LocalDateTime time;
    private int status;
    private String error;
    private String message;
}
