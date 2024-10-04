package com.oct.L3.exceptions;

import lombok.Getter;

@Getter
public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException(String message) {
        super(message);
    }


}
