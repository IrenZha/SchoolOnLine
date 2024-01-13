package com.example.blpayment.web;

import com.example.blpayment.dto.ErrorReasonResponse;
import com.example.blpayment.exc.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<ErrorReasonResponse> handleEntityFound(EntityNotFoundException exc){
        String entity = exc.getEntity();
        ErrorReasonResponse response = ErrorReasonResponse
                .builder()
                .entity(entity)
                .description("Payment not found")
                .build();
       return ResponseEntity.status(404).body(response);
    }
}
