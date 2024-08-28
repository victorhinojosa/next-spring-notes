package com.example.mindkeep.shared.infrastructure.web.api;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    public ErrorResponse(String message) {
        this(null, message);
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}