package com.example.mindkeep.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PositiveInteger extends ValueObject<Integer> {

    public PositiveInteger(Integer value) {
        super(value);
        ensureIsPositive(value);
    }

    private void ensureIsPositive(Integer value) {
        if (value < 0) {
            throw new IllegalArgumentException(String.format("The value <%s> is not a positive integer", value));
        }
    }
}
