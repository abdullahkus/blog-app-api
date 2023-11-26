package com.ak.blogapp.exception;

import lombok.Getter;

@Getter
public class InvalidEnumValueException extends IllegalArgumentException {
    private final String message;

    public InvalidEnumValueException(String enumValue) {
        super("Invalid enum value: " + enumValue);
        this.message = "Invalid enum value: " + enumValue;
    }
}

