package com.example.mindkeep.notes.domain;

import com.example.mindkeep.shared.domain.repositories.CrudRepository;

public interface NoteRepository extends CrudRepository<NoteId, Note> {

}
