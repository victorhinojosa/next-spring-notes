package com.example.notesapp.notes.infrastructure.web.dto;

import com.example.notesapp.shared.infrastructure.web.dto.DataTransferObject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDTO implements DataTransferObject {

    private String id;
    private String content;
    private LocalDateTime creationTIme;
}
