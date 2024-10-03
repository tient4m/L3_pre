package com.oct.L3.exceptions;

import lombok.Getter;

@Getter
public class InvalidStatusException extends RuntimeException {

    private String errorCode;

    public InvalidStatusException(String message) {
        super(message);
    }

    public InvalidStatusException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
