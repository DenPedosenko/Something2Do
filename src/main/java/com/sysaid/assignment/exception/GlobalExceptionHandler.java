package com.sysaid.assignment.exception;

import com.sysaid.assignment.domain.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ToManyActiveTaskException.class)
    public ResponseEntity<ErrorResponse> handleToManyActiveTaskException(ToManyActiveTaskException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage(), LocalDateTime.now()));
    }
}
