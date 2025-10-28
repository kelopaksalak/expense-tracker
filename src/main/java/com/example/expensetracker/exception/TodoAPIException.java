package com.example.expensetracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TodoAPIException extends RuntimeException {
    private final HttpStatus status;

    public TodoAPIException(HttpStatus status, String message) {
        super(message); // set RuntimeException’s message
        this.status = status;
    }
}

