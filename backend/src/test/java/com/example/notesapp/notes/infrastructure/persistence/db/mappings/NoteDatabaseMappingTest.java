package com.example.notesapp.notes.infrastructure.persistence.db.mappings;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.factories.NoteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteDatabaseMappingTest {

    @Nested
    @DisplayName("When creating a NoteDatabaseMapping")
    class CreationTests {

        @Test
        void should_throw_error_when_creating_it_without_a_note() {
            assertThrows(IllegalArgumentException.class, () -> new NoteDatabaseMapping(null));
        }

        @Test
        void should_create_with_note_values() {

            Note note = NoteMother.random();

            NoteDatabaseMapping databaseMapping = new NoteDatabaseMapping(note);

            assertAll(
                    () -> assertEquals(note.getId().value(), databaseMapping.getId()),
                    () -> assertEquals(note.getContent().value(), databaseMapping.getContent()),
                    () -> assertEquals(note.getCreationTime(), databaseMapping.getCreatedAt())
            );
        }
    }

    @Nested
    @DisplayName("When mapping to a Note")
    class DomainMapTests {

        @Test
        void should_map_to_a_note_correctly() {

            Note note = NoteMother.random();

            NoteDatabaseMapping databaseMapping = new NoteDatabaseMapping(note);

            assertEquals(note, databaseMapping.domainMap());
        }
    }
}