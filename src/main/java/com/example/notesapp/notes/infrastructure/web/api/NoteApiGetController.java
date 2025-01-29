package com.example.notesapp.notes.infrastructure.web.api;

import com.example.notesapp.config.RoutesConfig;
import com.example.notesapp.notes.application.NoteSearcher;
import com.example.notesapp.notes.domain.Note;
import com.example.notesapp.notes.infrastructure.web.dto.NoteDTO;
import com.example.notesapp.shared.infrastructure.web.api.ApiController;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoteApiGetController extends ApiController {

    private final NoteSearcher searcher;

    public NoteApiGetController(ModelMapper mapper, NoteSearcher searcher) {
        super(mapper);
        this.searcher = searcher;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(RoutesConfig.API.NOTES)
    public List<NoteDTO> search() {

        List<Note> notes = searcher.search();

        return map(notes, NoteDTO.class);
    }
}
