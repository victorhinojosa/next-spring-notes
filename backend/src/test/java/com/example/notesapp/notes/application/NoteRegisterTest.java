package com.example.notesapp.notes.application;

import com.example.notesapp.notes.domain.*;
import com.example.notesapp.notes.domain.factories.NoteRegistrationFormMother;
import com.example.notesapp.shared.domain.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class NoteRegisterTest {

    private NoteRepository repository;
    private Clock clock;
    private NoteRegister classUnderTest;

    @BeforeEach
    void setUp() {
        repository = mock(NoteRepository.class);
        clock = mock(Clock.class);

        classUnderTest = new NoteRegister(repository, clock);
    }

    @Nested
    @DisplayName("When registering a note")
    class RegisterTests {

        @Test
        void should_throw_error_when_form_is_null() {
            assertThrows(IllegalArgumentException.class, () -> classUnderTest.register(null));
        }

        @Test
        void should_throw_error_when_registration_form_is_invalid() {

            NoteRegistrationForm invalidForm = NoteRegistrationFormMother.withId(null);

            assertThrows(IllegalArgumentException.class, () -> classUnderTest.register(invalidForm));
        }

        @Test
        void should_register_note_correctly() {

            NoteRegistrationForm registrationForm = NoteRegistrationFormMother.random();
            LocalDateTime registrationTime = LocalDateTime.now();

            when(clock.now()).thenReturn(registrationTime);

            Note expectedNote = new Note(
                    new NoteId(registrationForm.getId()),
                    new NoteContent(registrationForm.getContent()),
                    registrationTime
            );

            classUnderTest.register(registrationForm);

            verify(repository, times(1)).save(expectedNote);
        }
    }
}