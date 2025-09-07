package com.example.notesapp.notes.domain.factories;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteContent;
import com.example.notesapp.notes.domain.NoteId;

import java.time.LocalDateTime;

public class NoteMother {

    public static Note random() {
        return create(NoteIdMother.random(), NoteContentMother.random(), LocalDateTime.now());
    }

    public static Note withId(NoteId value) {
        return create(value, NoteContentMother.random(), LocalDateTime.now());
    }

    public static Note withContent(NoteContent value) {
        return create(NoteIdMother.random(), value, LocalDateTime.now());
    }

    public static Note withCreationTime(LocalDateTime value) {
        return create(NoteIdMother.random(), NoteContentMother.random(), value);
    }

    public static Note create(NoteId id, NoteContent content, LocalDateTime creationTime) {
        return new Note(id, content, creationTime);
    }
}
