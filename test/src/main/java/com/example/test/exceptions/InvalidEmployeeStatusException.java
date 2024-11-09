package com.example.test.exceptions;

    public class InvalidEmployeeStatusException extends RuntimeException {
        public InvalidEmployeeStatusException(String message) {
            super(message);
        }
    }

