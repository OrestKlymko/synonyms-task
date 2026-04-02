package com.synonyms.task.exception.dto;


public record ExceptionResponse(
        int status,
        String message
) {
}
