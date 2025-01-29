package com.example.notesapp.shared.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class ValueObject<T> {

    protected final T value;

    protected ValueObject(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
