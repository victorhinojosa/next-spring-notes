package com.example.notesapp.notes.application;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteRepository;
import com.example.notesapp.notes.domain.factories.NoteMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteSearcherTest {

    private NoteRepository repository;
    private NoteSearcher classUnderTest;

    @BeforeEach
    void setUp() {
        repository = mock(NoteRepository.class);
        classUnderTest = new NoteSearcher(repository);
    }

    @Nested
    @DisplayName("When searching for notes")
    class SearchTests {

        @Test
        void should_return_an_empty_list_when_there_are_no_notes() {

            List<Note> notes = Collections.emptyList();

            when(repository.find()).thenReturn(notes);

            assertEquals(notes, classUnderTest.search());
        }

        @Test
        void should_return_existent_notes_sorted_by_creation_time_descending() {

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime earlier = now.minusHours(1);
            LocalDateTime latest = now.plusHours(1);

            Note oldestNote = NoteMother.withCreationTime(earlier);
            Note middleNote = NoteMother.withCreationTime(now);
            Note newestNote = NoteMother.withCreationTime(latest);

            // Repository returns unsorted
            List<Note> unsortedNotes = Arrays.asList(oldestNote, newestNote, middleNote);

            // Expected result: sorted by creation time descending (newest first)
            List<Note> expectedSortedNotes = Arrays.asList(newestNote, middleNote, oldestNote);

            when(repository.find()).thenReturn(unsortedNotes);

            List<Note> result = classUnderTest.search();

            assertEquals(expectedSortedNotes, result);
        }
    }
}