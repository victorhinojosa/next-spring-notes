package com.example.notesapp.notes.domain.factories;

import com.example.notesapp.notes.domain.NoteEditForm;
import net.andreinc.mockneat.MockNeat;

public class NoteEditFormMother {

    public static NoteEditForm random() {
        return create(MockNeat.threadLocal().words().get());
    }

    public static NoteEditForm withContent(String value) {
        return create(value);
    }

    public static NoteEditForm create(String content) {
        return new NoteEditForm(content);
    }
}
