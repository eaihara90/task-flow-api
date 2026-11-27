package com.taskflow.task.exception;

import com.taskflow.task.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleTaskNotFoundException(TaskNotFoundException e) {
        var statusCode = HttpStatus.NOT_FOUND.value();
        var response = new ApiErrorResponseDto(
                statusCode,
                e.getMessage(),
                new Date(),
                null
        );

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        var statusCode = HttpStatus.BAD_REQUEST.value();
        Map<String, List<String>> errors = new HashMap<>();

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        fieldErrors.forEach((fieldError) -> errors.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>()).add(fieldError.getDefaultMessage()));

        var response = new ApiErrorResponseDto(
                statusCode,
                e.getMessage(),
                new Date(),
                errors
        );

        return ResponseEntity
                .status(statusCode)
                .body(response);
    }

    @ExceptionHandler(InvalidTaskStatusTransitionException.class)
    public ResponseEntity<ApiErrorResponseDto> handleInvalidTaskStatusTransitionException(InvalidTaskStatusTransitionException e) {
        var statusCode = HttpStatus.CONFLICT.value();
        var response = new ApiErrorResponseDto(
                statusCode,
                e.getMessage(),
                new Date(),
                null
        );

        return ResponseEntity.status(statusCode).body(response);
    }
}