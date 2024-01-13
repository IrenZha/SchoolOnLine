package com.example.blusers.web;

import com.example.blusers.dto.ErrorReasonResponse;
import com.example.blusers.exc.EntityNotFoundException;
import com.example.blusers.exc.NotPaidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorReasonResponse> handleEntityFound(EntityNotFoundException exc) {
        String entity = exc.getEntity();
        ErrorReasonResponse response = ErrorReasonResponse
                .builder()
                .entity(entity)
                .description("User not found")
                .build();
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(NotPaidException.class)
        public ResponseEntity<ErrorReasonResponse> notPaid(){
        ErrorReasonResponse response = ErrorReasonResponse
                .builder()
                .description("Insufficient funds")
                .build();
        return ResponseEntity.status(400).body(response);
    }
}
