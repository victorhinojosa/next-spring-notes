package com.example.mindkeep.notes.application;

import com.example.mindkeep.notes.domain.Note;
import com.example.mindkeep.notes.domain.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NoteSearcher {

    private final NoteRepository repository;

    public NoteSearcher(NoteRepository repository) {
        this.repository = repository;
    }

    public List<Note> search() {
        List<Note> notes = repository.find();
        notes.sort(Comparator.comparing(Note::getCreationTime).reversed());
        return notes;
    }
}
