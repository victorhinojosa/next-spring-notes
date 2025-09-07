package com.example.notesapp.notes.domain;

import com.example.notesapp.notes.domain.factories.NoteContentMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteContentTest {

    @Test
    void should_throw_error_when_creating_it_with_a_blank_value() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> NoteContentMother.create(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> NoteContentMother.create("")),
                () -> assertThrows(IllegalArgumentException.class, () -> NoteContentMother.create(" "))
        );
    }

    @Test
    void should_create_with_specified_content() {

        String value = "This is a note content";

        NoteContent content = NoteContentMother.create(value);

        assertEquals(value, content.value());
    }
}