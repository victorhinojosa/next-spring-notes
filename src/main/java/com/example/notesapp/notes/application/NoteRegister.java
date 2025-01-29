package com.example.notesapp.notes.application;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteRepository;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.notes.domain.NoteContent;
import com.example.notesapp.notes.domain.NoteRegistrationForm;
import com.example.notesapp.shared.domain.Clock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class NoteRegister{

    private final NoteRepository repository;
    private final Clock clock;

    public NoteRegister(NoteRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public Note register(NoteRegistrationForm registrationform) {
        
        validate(registrationform);

        log.info("Registering note with id: {}", registrationform.getId());

        Note note = new Note(
            new NoteId(registrationform.getId()),
            new NoteContent(registrationform.getContent()),
            clock.now()
        );

        repository.save(note);
        log.info("Note has been registered successfully");

        return note;
    }

    private void validate(NoteRegistrationForm registrationform) {
        if (registrationform == null || !registrationform.isValid()) {
            throw new IllegalArgumentException("Invalid registration form");
        }
    }
}
