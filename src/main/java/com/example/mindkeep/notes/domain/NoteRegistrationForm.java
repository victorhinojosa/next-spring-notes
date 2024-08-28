package com.example.mindkeep.notes.domain;

import com.example.mindkeep.shared.domain.Form;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRegistrationForm implements Form {
    private String id;
    private String content;

    @Override
    public boolean isValid() {
        try {
            new NoteId(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
