package com.example.notesapp.notes.infrastructure.web.api;

import com.example.notesapp.config.RoutesConfig;
import com.example.notesapp.notes.application.NoteSearcher;
import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.factories.NoteMother;
import com.example.notesapp.notes.infrastructure.ControllerTest;
import com.example.notesapp.notes.infrastructure.web.dto.NoteDTO;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoteApiGetControllerTest extends ControllerTest<NoteApiGetController> {

    private NoteSearcher noteSearcher;

    @Override
    protected NoteApiGetController instantiateController() {
        return new NoteApiGetController(mapper, noteSearcher);
    }

    @Override
    @BeforeEach
    protected void setUp() {
        noteSearcher = mock(NoteSearcher.class);
        super.setUp();
    }

    @Nested
    @DisplayName("GET" + RoutesConfig.API.NOTES)
    class GetNotesTests {

        @Test
        void should_return_internal_server_error_when_an_exception_occurs() throws Exception {

            doThrow(new RuntimeException()).when(noteSearcher).search();

            mockMvc.perform(get(RoutesConfig.API.NOTES))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void should_return_ok_with_notes_as_payload() throws Exception {

            List<Note> notes = List.of(
                    NoteMother.random(),
                    NoteMother.random()
            );

            when(noteSearcher.search()).thenReturn(notes);

            String expectedResponse = serializeAsJson(map(notes, NoteDTO.class));

            mockMvc.perform(get(RoutesConfig.API.NOTES))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expectedResponse));
        }
    }
}