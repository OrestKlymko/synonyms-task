package com.synonyms.task.exception.handler;


import com.synonyms.task.exception.dto.ExceptionResponse;
import com.synonyms.task.exception.model.BadRequestException;
import com.synonyms.task.exception.model.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return buildResponseEntity(HttpStatus.BAD_REQUEST, message);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> buildResponseEntity(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponse(status.value(), message));
    }

}
