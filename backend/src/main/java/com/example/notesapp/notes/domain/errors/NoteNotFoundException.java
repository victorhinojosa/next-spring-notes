package com.example.notesapp.notes.domain.errors;

import com.example.notesapp.shared.domain.errors.NotFoundException;
import com.example.notesapp.notes.domain.NoteId;

public class NoteNotFoundException extends NotFoundException {

    public NoteNotFoundException() {
        super("Note not found");
    }

    public NoteNotFoundException(NoteId id) {
        super("Note with id " + id.value() + " not found");
    }
}
