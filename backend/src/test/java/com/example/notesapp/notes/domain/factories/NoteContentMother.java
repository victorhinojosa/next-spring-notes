package com.example.notesapp.notes.domain.factories;

import com.example.notesapp.notes.domain.NoteContent;
import net.andreinc.mockneat.MockNeat;

public class NoteContentMother {

    public static NoteContent random() {
        return create(MockNeat.threadLocal().words().get());
    }

    public static NoteContent create(String value) {
        return new NoteContent(value);
    }
}
