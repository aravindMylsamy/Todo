package com.springboot.todo.controller;

import com.springboot.todo.exception.DuplicateEntryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateEntryException(DuplicateEntryException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
