package com.example.mindkeep.notes.infrastructure.persistence.db;

import com.example.mindkeep.notes.domain.Note;
import com.example.mindkeep.notes.domain.NoteId;
import com.example.mindkeep.notes.domain.NoteRepository;
import com.example.mindkeep.notes.infrastructure.persistence.db.spring.SpringDataJpaNoteRepository;
import com.example.mindkeep.notes.infrastructure.persistence.db.mappings.NoteDatabaseMapping;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.mindkeep.shared.domain.PropertyValidator.ensureNotNull;
@Repository
public class DatabaseNoteRepository implements NoteRepository {

    private final SpringDataJpaNoteRepository springRepository;

    public DatabaseNoteRepository(SpringDataJpaNoteRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Optional<Note> find(NoteId id) {

        ensureNotNull(id, "A note id is required to perform this operation");

        return springRepository.findById(id.value())
                .map(NoteDatabaseMapping::domainMap);
    }

    @Override
    public List<Note> find() {

        return springRepository.findAll().stream()
                .map(NoteDatabaseMapping::domainMap)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Note note) {

        ensureNotNull(note, "A note is required to perform this operation");
        springRepository.save(new NoteDatabaseMapping(note));
    }

    @Override
    public void delete(NoteId id) {

        ensureNotNull(id, "A note id is required to perform this operation");
        springRepository.deleteById(id.value());
    }
}
