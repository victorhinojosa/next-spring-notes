package com.example.notesapp.notes.infrastructure.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class NoteViewController {

    @GetMapping({"/", "/notes"})
    public String index() {
        return "index";
    }  
}
