package com.example.mindkeep.notes.infrastructure.web.api;

import com.example.mindkeep.config.RoutesConfig;
import com.example.mindkeep.notes.application.NoteSearcher;
import com.example.mindkeep.notes.domain.Note;
import com.example.mindkeep.notes.infrastructure.web.dto.NoteDTO;
import com.example.mindkeep.shared.infrastructure.web.api.ApiController;
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
