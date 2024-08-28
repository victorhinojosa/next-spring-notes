package com.example.mindkeep.notes.domain;

import com.example.mindkeep.shared.domain.Identifier;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NoteId extends Identifier {
    public NoteId(String value) {
        super(value);
    }
}
