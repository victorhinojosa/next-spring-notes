package com.example.notesapp.notes.infrastructure.persistence.db.mappings;

import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.domain.NoteContent;
import com.example.notesapp.notes.domain.NoteId;
import com.example.notesapp.shared.infrastructure.persistence.DatabaseMapping;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class NoteDatabaseMapping implements DatabaseMapping<Note> {

    @Id
    private String id;

    private String content;
    private LocalDateTime createdAt;

    public NoteDatabaseMapping(Note note) {
        mapValues(note);
    }

    @Override
    public Note domainMap() {
        return Note.deserialize(
                new NoteId(id),
                new NoteContent(content),
                createdAt
        );
    }

    private void mapValues(Note note) {

        if (note == null) {
            throw new IllegalArgumentException("A note is required to create a database mapping");
        }

        id = note.getId().value();
        content = note.getContent().value();
        createdAt = note.getCreationTime();
    }
}