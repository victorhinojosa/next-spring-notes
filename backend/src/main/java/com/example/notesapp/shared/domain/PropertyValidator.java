package com.example.notesapp.shared.domain;

import java.util.Collection;

public class PropertyValidator {

    public static void ensureNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void ensureNotEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void ensurePositive(int value, String message) {
        if (value <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void ensureNonNegative(int value, String message) {
        if (value < 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
