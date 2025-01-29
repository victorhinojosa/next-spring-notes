package com.example.notesapp.notes.infrastructure.web.api;

import com.example.notesapp.config.RoutesConfig;
import com.example.notesapp.notes.application.NoteEditor;
import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteEditForm;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.notes.infrastructure.web.dto.NoteDTO;
import com.example.notesapp.shared.infrastructure.web.api.ApiController;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteApiPatchController extends ApiController {

    private final NoteEditor editor;

    public NoteApiPatchController(ModelMapper mapper, NoteEditor editor) {
        super(mapper);
        this.editor = editor;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(RoutesConfig.API.NOTE)
    public NoteDTO edit(@PathVariable String noteId, @RequestBody NoteEditForm form) {

        NoteId id = new NoteId(noteId);

        Note editedNote = editor.edit(id, form);

        return mapper.map(editedNote, NoteDTO.class);
    }
}
