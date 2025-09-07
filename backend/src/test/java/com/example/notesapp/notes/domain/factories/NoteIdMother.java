package com.example.notesapp.notes.domain.factories;

import com.example.notesapp.notes.domain.NoteId;

import java.util.UUID;

public class NoteIdMother {

    public static NoteId random() {
        return create(UUID.randomUUID().toString());
    }

    public static NoteId create(String value) {
        return new NoteId(value);
    }
}
