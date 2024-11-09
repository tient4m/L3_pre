package com.oct.l3.exceptions;

    public class InvalidEmployeeStatusException extends RuntimeException {
        public InvalidEmployeeStatusException(String message) {
            super(message);
        }
    }

