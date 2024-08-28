package com.example.mindkeep.notes.domain;

import com.example.mindkeep.shared.domain.ValueObject;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NoteContent extends ValueObject<String> {
    
    public NoteContent(String value) {
        super(value);
        validate(value);
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Note content is required");
        }
    }
}
