package com.example.notesapp.notes.domain;

import com.example.notesapp.shared.domain.Form;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteEditForm implements Form {

    private String content;

    @Override
    public boolean isValid() {
        try {
            new NoteContent(content);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
