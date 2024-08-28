package com.example.mindkeep.shared.domain.errors;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("You are not authorized to perform this action");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}