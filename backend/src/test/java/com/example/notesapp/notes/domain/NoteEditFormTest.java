package com.example.notesapp.notes.domain;

import com.example.notesapp.notes.domain.factories.NoteContentMother;
import com.example.notesapp.notes.domain.factories.NoteEditFormMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteEditFormTest {

    @Nested
    @DisplayName("When creating")
    class CreationTests {

        @Test
        void should_create_with_specific_content() {

            String content = NoteContentMother.random().value();

            NoteEditForm editForm = NoteEditFormMother.withContent(content);

            assertEquals(content, editForm.getContent());
        }
    }

    @Nested
    @DisplayName("When validating")
    class ValidationTests {

        @Test
        void should_be_invalid_when_content_is_null() {
            assertFalse(NoteEditFormMother.withContent(null).isValid());
        }

        @Test
        void should_be_invalid_when_content_is_blank() {
            assertFalse(NoteEditFormMother.withContent("").isValid());
        }

        @Test
        void should_be_valid_when_content_has_text() {
            assertTrue(NoteEditFormMother.withContent("Valid content").isValid());
        }
    }
}