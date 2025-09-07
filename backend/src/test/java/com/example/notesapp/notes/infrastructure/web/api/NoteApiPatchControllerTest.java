package com.example.notesapp.notes.infrastructure.web.api;

import com.example.notesapp.config.RoutesConfig;
import com.example.notesapp.notes.application.NoteEditor;
import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteEditForm;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.notes.domain.errors.NoteNotFoundException;
import com.example.notesapp.notes.domain.factories.NoteEditFormMother;
import com.example.notesapp.notes.domain.factories.NoteIdMother;
import com.example.notesapp.notes.domain.factories.NoteMother;
import com.example.notesapp.notes.infrastructure.ControllerTest;
import com.example.notesapp.notes.infrastructure.web.dto.NoteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoteApiPatchControllerTest extends ControllerTest<NoteApiPatchController> {

    private NoteEditor noteEditor;

    @Override
    protected NoteApiPatchController instantiateController() {
        return new NoteApiPatchController(mapper, noteEditor);
    }

    @Override
    @BeforeEach
    protected void setUp() {
        noteEditor = mock(NoteEditor.class);
        super.setUp();
    }

    @Nested
    @DisplayName("PATCH" + RoutesConfig.API.NOTE)
    class EditTests {

        @Test
        void should_return_bad_request_when_id_is_invalid() throws Exception {

            String invalidId = "invalid-id";
            NoteEditForm editForm = NoteEditFormMother.random();

            String requestPayload = serializeAsJson(editForm);

            mockMvc.perform(
                            patch(RoutesConfig.API.NOTE, invalidId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isBadRequest());
        }

        @Test
        void should_return_internal_server_error_when_an_exception_occurs() throws Exception {

            NoteId noteId = NoteIdMother.random();
            NoteEditForm editForm = NoteEditFormMother.random();

            doThrow(new RuntimeException()).when(noteEditor).edit(any(), any());

            String requestPayload = serializeAsJson(editForm);

            mockMvc.perform(
                            patch(RoutesConfig.API.NOTE, noteId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void should_return_bad_request_when_an_illegal_argument_exception_occurs() throws Exception {

            NoteId noteId = NoteIdMother.random();
            NoteEditForm editForm = NoteEditFormMother.random();

            doThrow(new IllegalArgumentException()).when(noteEditor).edit(any(), any());

            String requestPayload = serializeAsJson(editForm);

            mockMvc.perform(
                            patch(RoutesConfig.API.NOTE, noteId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isBadRequest());
        }

        @Test
        void should_return_not_found_when_note_didnt_exist() throws Exception {

            NoteId noteId = NoteIdMother.random();
            NoteEditForm editForm = NoteEditFormMother.random();

            doThrow(new NoteNotFoundException()).when(noteEditor).edit(any(), any());

            String requestPayload = serializeAsJson(editForm);

            mockMvc.perform(
                            patch(RoutesConfig.API.NOTE, noteId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isNotFound());
        }

        @Test
        void should_return_ok_when_all_is_ok() throws Exception {

            NoteId noteId = NoteIdMother.random();
            NoteEditForm editForm = NoteEditFormMother.random();
            Note expectedNote = NoteMother.withId(noteId);

            when(noteEditor.edit(noteId, editForm)).thenReturn(expectedNote);

            String expectedResponsePayload = serializeAsJson(mapper.map(expectedNote, NoteDTO.class));

            mockMvc.perform(
                            patch(RoutesConfig.API.NOTE, noteId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(serializeAsJson(editForm))
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().string(expectedResponsePayload));

        }
    }
}