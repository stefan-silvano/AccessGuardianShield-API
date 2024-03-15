package com.cybersecurity.iam.exception.type;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}