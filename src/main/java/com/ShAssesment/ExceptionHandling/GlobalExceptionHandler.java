package com.ShAssesment.ExceptionHandling;

import com.ShAssesment.Response.SHResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<SHResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        SHResponse<String> response = new SHResponse<>(null, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DateTimeParseException.class) 
    public ResponseEntity<SHResponse<String>> handleDateTimeParseException(DateTimeParseException ex) {
        String errorMessage = "Invalid date format or value.";
        SHResponse<String> response = new SHResponse<>(null, errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) 
    public ResponseEntity<SHResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String errorMessage = "Invalid request body. Please check the input format and try again.";
        SHResponse<String> response = new SHResponse<>(null, errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SHResponse<String>> handleGenericException(Exception ex) {
        SHResponse<String> response = new SHResponse<>(null, "An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
