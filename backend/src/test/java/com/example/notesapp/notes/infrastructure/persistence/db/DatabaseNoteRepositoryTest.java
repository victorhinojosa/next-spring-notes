package com.example.notesapp.notes.infrastructure.persistence.db;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.notes.domain.factories.NoteIdMother;
import com.example.notesapp.notes.domain.factories.NoteMother;
import com.example.notesapp.notes.infrastructure.persistence.db.mappings.NoteDatabaseMapping;
import com.example.notesapp.notes.infrastructure.persistence.db.spring.SpringDataJpaNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class DatabaseNoteRepositoryTest {

    private SpringDataJpaNoteRepository springRepository;
    private DatabaseNoteRepository classUnderTest;

    @BeforeEach
    void setUp() {
        springRepository = mock(SpringDataJpaNoteRepository.class);
        classUnderTest = new DatabaseNoteRepository(springRepository);
    }

    @Nested
    @DisplayName("When finding a note by id")
    class FindByIdTests {

        @Test
        void should_throw_error_when_trying_to_find_a_note_without_passing_an_id() {
            assertThrows(IllegalArgumentException.class, () -> classUnderTest.find(null));
        }

        @Test
        void should_not_find_a_note_that_does_not_exist() {

            NoteId noteId = NoteIdMother.random();

            when(springRepository.findById(noteId.value())).thenReturn(Optional.empty());

            assertEquals(Optional.empty(), classUnderTest.find(noteId));
        }

        @Test
        void should_find_a_note_that_exists() {

            Note note = NoteMother.random();

            when(springRepository.findById(note.getId().value())).thenReturn(Optional.of(new NoteDatabaseMapping(note)));

            assertEquals(Optional.of(note), classUnderTest.find(note.getId()));
        }
    }

    @Nested
    @DisplayName("When finding all notes")
    class FindAllTests {

        @Test
        void should_find_all_notes() {

            List<Note> expectedNotes = List.of(NoteMother.random(), NoteMother.random());

            List<NoteDatabaseMapping> noteDatabaseMappings = expectedNotes.stream()
                    .map(NoteDatabaseMapping::new)
                    .toList();

            when(springRepository.findAll()).thenReturn(noteDatabaseMappings);

            assertEquals(expectedNotes, classUnderTest.find());
        }
    }

    @Nested
    @DisplayName("When saving a Note")
    class SaveTests {

        @Test
        void should_throw_error_when_trying_to_save_a_null_note() {
            assertThrows(IllegalArgumentException.class, () -> classUnderTest.save(null));
        }

        @Test
        void should_save_a_note() {

            Note note = NoteMother.random();

            classUnderTest.save(note);

            verify(springRepository).save(new NoteDatabaseMapping(note));
        }
    }

    @Nested
    @DisplayName("When deleting a note")
    class DeleteByIdTests {

        @Test
        void should_throw_error_when_trying_to_delete_a_note_without_passing_an_id() {
            assertThrows(IllegalArgumentException.class, () -> classUnderTest.delete(null));
        }

        @Test
        void should_delete_a_note() {

            NoteId noteId = NoteIdMother.random();

            classUnderTest.delete(noteId);

            verify(springRepository).deleteById(noteId.value());
        }
    }
}