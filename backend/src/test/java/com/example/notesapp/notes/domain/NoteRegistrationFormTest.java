package com.example.notesapp.notes.domain;

import com.example.notesapp.notes.domain.factories.NoteContentMother;
import com.example.notesapp.notes.domain.factories.NoteIdMother;
import com.example.notesapp.notes.domain.factories.NoteRegistrationFormMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteRegistrationFormTest {

    @Nested
    @DisplayName("When creating")
    class CreationTests {

        @Test
        void should_create_with_specific_id() {

            String id = NoteIdMother.random().value();

            NoteRegistrationForm registrationForm = NoteRegistrationFormMother.withId(id);

            assertEquals(id, registrationForm.getId());
        }

        @Test
        void should_create_with_specific_content() {

            String content = NoteContentMother.random().value();

            NoteRegistrationForm registrationForm = NoteRegistrationFormMother.withContent(content);

            assertEquals(content, registrationForm.getContent());
        }
    }

    @Nested
    @DisplayName("When validating")
    class ValidationTests {

        @Test
        void should_be_invalid_when_id_id_blank() {
            assertFalse(NoteRegistrationFormMother.withId(null).isValid());
        }

        @Test
        void should_be_invalid_when_id_is_invalid() {
            assertFalse(NoteRegistrationFormMother.withId("invalid-id").isValid());
        }

        @Test
        void should_be_invalid_when_content_is_null() {
            assertFalse(NoteRegistrationFormMother.withContent(null).isValid());
        }

        @Test
        void should_be_invalid_when_content_is_blank() {
            assertFalse(NoteRegistrationFormMother.withContent("").isValid());
        }

        @Test
        void should_be_valid_when_all_data_is_ok() {
            assertTrue(NoteRegistrationFormMother.random().isValid());
        }
    }
}