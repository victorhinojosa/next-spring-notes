package com.example.notesapp.notes.domain.factories;

import com.example.notesapp.notes.domain.NoteRegistrationForm;

public class NoteRegistrationFormMother {

    public static NoteRegistrationForm random() {
        return create(NoteIdMother.random().value(), NoteContentMother.random().value());
    }

    public static NoteRegistrationForm withId(String value) {
        return create(value, NoteContentMother.random().value());
    }

    public static NoteRegistrationForm withContent(String value) {
        return create(NoteIdMother.random().value(), value);
    }

    public static NoteRegistrationForm create(String id, String content) {
        return new NoteRegistrationForm(id, content);
    }
}
