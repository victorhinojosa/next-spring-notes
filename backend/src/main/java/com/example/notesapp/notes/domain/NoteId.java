package com.example.notesapp.notes.domain;

import com.example.notesapp.shared.domain.Identifier;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NoteId extends Identifier {
    public NoteId(String value) {
        super(value);
    }
}
