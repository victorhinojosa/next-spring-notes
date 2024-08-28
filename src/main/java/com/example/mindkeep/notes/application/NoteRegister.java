package com.example.mindkeep.notes.application;

import com.example.mindkeep.notes.domain.Note;
import com.example.mindkeep.notes.domain.NoteRepository;
import com.example.mindkeep.notes.domain.NoteId;
import com.example.mindkeep.notes.domain.NoteContent;
import com.example.mindkeep.notes.domain.NoteRegistrationForm;
import com.example.mindkeep.shared.domain.Clock;
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
