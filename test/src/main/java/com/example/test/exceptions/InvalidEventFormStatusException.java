package com.example.test.exceptions;

public class InvalidEventFormStatusException extends RuntimeException {
    public InvalidEventFormStatusException(String message) {
        super(message);
    }
}
