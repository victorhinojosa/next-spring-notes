package com.example.mindkeep.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NonEmptyString extends ValueObject<String> {

    public NonEmptyString(String value) {
        super(validate(value));
    }

    private static String validate(String value) {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("The value can't be null");
        }
        return value.trim();
    }
}
