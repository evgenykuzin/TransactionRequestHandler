package com.alpha.exceptions;

public class IllegalDataException extends IllegalArgumentException {
    public IllegalDataException(String message) {
        super(message);
    }

    public IllegalDataException() {
    }
}
