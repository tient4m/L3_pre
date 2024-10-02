package com.oct.L3.exceptions;

public class InvalidEmployeeStatusException extends RuntimeException {
    public InvalidEmployeeStatusException(String message) {
        super(message);
    }
}

