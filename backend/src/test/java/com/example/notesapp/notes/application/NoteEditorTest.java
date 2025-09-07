package com.example.notesapp.notes.application;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteEditForm;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.notes.domain.NoteRepository;
import com.example.notesapp.notes.domain.errors.NoteNotFoundException;
import com.example.notesapp.notes.domain.factories.NoteEditFormMother;
import com.example.notesapp.notes.domain.factories.NoteIdMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteEditorTest {

    private NoteRepository repository;
    private NoteEditor classUnderTest;

    @BeforeEach
    void setUp() {
        repository = mock(NoteRepository.class);
        classUnderTest = new NoteEditor(repository);
    }

    @Nested
    @DisplayName("When editing a note")
    class EditTests {

        @Test
        void should_throw_error_when_id_is_null() {
            assertThrows(IllegalArgumentException.class, () -> classUnderTest.edit(null, NoteEditFormMother.random()));
        }

        @Test
        void should_throw_error_when_edit_form_is_null() {
            assertThrows(IllegalArgumentException.class, () -> classUnderTest.edit(NoteIdMother.random(), null));
        }

        @Test
        void should_throw_error_when_edit_form_is_invalid() {

            NoteEditForm invalidEditForm = NoteEditFormMother.withContent(null);

            assertThrows(IllegalArgumentException.class, () -> classUnderTest.edit(NoteIdMother.random(), invalidEditForm));
        }

        @Test
        void should_throw_error_when_specified_note_does_not_exist() {

            NoteId id = NoteIdMother.random();
            NoteEditForm editForm = NoteEditFormMother.random();

            when(repository.find(id)).thenReturn(Optional.empty());

            assertThrows(NoteNotFoundException.class, () -> classUnderTest.edit(id, editForm));
        }

        @Test
        void should_edit_a_note_and_save_changes() {

            NoteId id = NoteIdMother.random();
            NoteEditForm editForm = NoteEditFormMother.random();
            Note existentNote = mock(Note.class);

            when(repository.find(id)).thenReturn(Optional.of(existentNote));

            Note note = classUnderTest.edit(id, editForm);

            assertEquals(note, existentNote);

            verify(existentNote, times(1)).edit(editForm);
            verify(repository, times(1)).save(existentNote);
        }
    }
}