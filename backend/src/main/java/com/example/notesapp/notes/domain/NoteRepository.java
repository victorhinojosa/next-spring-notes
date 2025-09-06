package com.example.notesapp.notes.domain;

import com.example.notesapp.shared.domain.repositories.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteId, Note> {

}
