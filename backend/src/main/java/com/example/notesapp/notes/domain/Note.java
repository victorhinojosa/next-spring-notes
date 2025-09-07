package com.example.notesapp.notes.domain;

import com.example.notesapp.shared.domain.AggregateRoot;
import lombok.Getter;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Note extends AggregateRoot<NoteId> {

    private final NoteId id;
    private NoteContent content;
    private final LocalDateTime creationTime;

    public Note(NoteId id, NoteContent content, LocalDateTime creationTime) {

        if (id == null) {
            throw new IllegalArgumentException("Note id is required");
        }
        if (content == null) {
            throw new IllegalArgumentException("Note content is required");
        }
        if (creationTime == null) {
            throw new IllegalArgumentException("Creation time is required");
        }

        this.id = id;
        this.content = content;
        this.creationTime = creationTime;
    }

    public void edit(NoteEditForm editForm) {
        if (editForm == null || !editForm.isValid()) {
            throw new IllegalArgumentException("Invalid edit form");
        }
        this.content = new NoteContent(editForm.getContent());
    }

    public static Note deserialize(NoteId id, NoteContent content, LocalDateTime creationTime) {
        return new Note(id, content, creationTime);
    }
}
