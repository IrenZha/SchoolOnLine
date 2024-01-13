package com.example.bl_courses.web;

import com.example.bl_courses.dto.ErrorReasonResponse;
import com.example.bl_courses.exc.EntityNotFoundException;
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
                .description("Course or category not found")
                .build();
       return ResponseEntity.status(404).body(response);
    }
}
