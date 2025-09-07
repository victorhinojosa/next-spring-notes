package com.example.notesapp.notes.domain;

import com.example.notesapp.notes.domain.factories.NoteContentMother;
import com.example.notesapp.notes.domain.factories.NoteEditFormMother;
import com.example.notesapp.notes.domain.factories.NoteIdMother;
import com.example.notesapp.notes.domain.factories.NoteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Nested
    @DisplayName("When creating a note")
    class CreationTests {

        @Test
        void should_throw_error_when_creating_it_without_id() {
            assertThrows(IllegalArgumentException.class, () -> NoteMother.withId(null));
        }

        @Test
        void should_throw_error_when_creating_it_without_content() {
            assertThrows(IllegalArgumentException.class, () -> NoteMother.withContent(null));
        }

        @Test
        void should_create_with_specified_id() {

            NoteId id = NoteIdMother.random();

            Note note = NoteMother.withId(id);

            assertEquals(id, note.getId());
        }

        @Test
        void should_create_with_specified_content() {

            NoteContent content = NoteContentMother.random();

            Note note = NoteMother.withContent(content);

            assertEquals(content, note.getContent());
        }

        @Test
        void should_create_with_specified_creation_time() {

            LocalDateTime creationTime = LocalDateTime.now();

            Note note = NoteMother.withCreationTime(creationTime);

            assertEquals(creationTime, note.getCreationTime());
        }
    }

    @Nested
    @DisplayName("When editing a note")
    class EditTests {

        @Test
        void should_throw_error_when_edit_form_is_null() {

            Note note = NoteMother.random();

            assertThrows(IllegalArgumentException.class, () -> note.edit(null));
        }

        @Test
        void should_throw_error_when_edit_form_is_invalid() {

            Note note = NoteMother.random();

            NoteEditForm invalidEditForm = NoteEditFormMother.withContent(null);

            assertThrows(IllegalArgumentException.class, () -> note.edit(invalidEditForm));
        }

        @Test
        void should_edit_with_form_values() {

            Note note = NoteMother.random();

            NoteEditForm editForm = NoteEditFormMother.random();

            note.edit(editForm);

            assertEquals(editForm.getContent(), note.getContent().value());
        }
    }

    @Nested
    @DisplayName("When deserializing a note")
    class DeserializationTests {

        @Test
        void should_deserialize_with_specified_values() {

            Note note = NoteMother.random();

            Note deserializedNote = Note.deserialize(
                    note.getId(),
                    note.getContent(),
                    note.getCreationTime()
            );

            assertEquals(note, deserializedNote);
        }
    }
}