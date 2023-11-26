package com.ak.blogapp.exception;

public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message) {
        super(message);
    }
}