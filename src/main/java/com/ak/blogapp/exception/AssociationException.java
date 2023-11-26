package com.ak.blogapp.exception;

import lombok.Getter;

@Getter
public class AssociationException extends RuntimeException {
    private final String message;

    public AssociationException(String message) {
        this.message = message;
    }

    public AssociationException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
