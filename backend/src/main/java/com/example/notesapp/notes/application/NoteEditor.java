package com.example.notesapp.notes.application;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteRepository;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.notes.domain.NoteEditForm;
import com.example.notesapp.notes.domain.errors.NoteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NoteEditor {

    private final NoteRepository repository;

    public NoteEditor(NoteRepository repository) {
        this.repository = repository;
    }

    public Note edit(NoteId id, NoteEditForm editForm) {

        validateInput(id, editForm);

        log.info("Editing note with id: {}", id.value());

        Note note = repository.find(id).orElseThrow(() -> new NoteNotFoundException(id));

        note.edit(editForm);
        repository.save(note);

        log.info("Note with id {} has been edited successfully", id.value());
        
        return note;
    }

    private void validateInput(NoteId id, NoteEditForm editForm) {
        if (id == null) {
            throw new IllegalArgumentException("A note id is required to edit a note");
        }
        if (editForm == null || !editForm.isValid()) {
            throw new IllegalArgumentException("A valid note edit form is required to edit a note");
        }
    }
}