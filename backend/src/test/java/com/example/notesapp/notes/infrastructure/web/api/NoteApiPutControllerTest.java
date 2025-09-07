package com.example.notesapp.notes.infrastructure.web.api;

import com.example.notesapp.config.RoutesConfig;
import com.example.notesapp.notes.application.NoteRegister;
import com.example.notesapp.notes.domain.NoteRegistrationForm;
import com.example.notesapp.notes.domain.factories.NoteRegistrationFormMother;
import com.example.notesapp.notes.infrastructure.ControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NoteApiPutControllerTest extends ControllerTest<NoteApiPutController> {

    private NoteRegister noteRegister;

    @Override
    @BeforeEach
    protected void setUp() {
        noteRegister = mock(NoteRegister.class);
        super.setUp();
    }

    @Override
    protected NoteApiPutController instantiateController() {
        return new NoteApiPutController(mapper, noteRegister);
    }

    @Nested
    @DisplayName("PUT" + RoutesConfig.API.NOTES)
    class RegisterTests {

        @Test
        void should_return_internal_server_error_when_an_exception_occurs() throws Exception {

            NoteRegistrationForm registrationForm = NoteRegistrationFormMother.random();

            doThrow(new RuntimeException()).when(noteRegister).register(any());

            String requestPayload = serializeAsJson(registrationForm);

            mockMvc.perform(
                            put(RoutesConfig.API.NOTES)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void should_return_bad_request_when_an_illegal_argument_exception_occurs() throws Exception {

            NoteRegistrationForm invalidRegistrationForm = NoteRegistrationFormMother.withId(null);

            doThrow(new IllegalArgumentException()).when(noteRegister).register(any());

            String requestPayload = serializeAsJson(invalidRegistrationForm);

            mockMvc.perform(
                            put(RoutesConfig.API.NOTES)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isBadRequest());
        }

        @Test
        void should_return_created_when_all_is_ok() throws Exception {

            NoteRegistrationForm registrationForm = NoteRegistrationFormMother.random();

            String requestPayload = serializeAsJson(registrationForm);

            mockMvc.perform(
                            put(RoutesConfig.API.NOTES)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(requestPayload)
                    )
                    .andExpect(status().isCreated());

            verify(noteRegister, times(1)).register(registrationForm);
        }
    }
}