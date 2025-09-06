package com.example.notesapp.shared.domain;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
public class Identifier extends ValueObject<String> {

    public Identifier(String value) {
        super(value);
        ensureValid(value);
    }

    private void ensureValid(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Identifier can't be null");
        }

        UUID.fromString(value);
    }


}