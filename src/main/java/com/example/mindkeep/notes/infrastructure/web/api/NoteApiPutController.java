package com.example.mindkeep.notes.infrastructure.web.api;

import com.example.mindkeep.config.RoutesConfig;
import com.example.mindkeep.notes.application.NoteRegister;
import com.example.mindkeep.notes.domain.NoteRegistrationForm;
import com.example.mindkeep.shared.infrastructure.web.api.ApiController;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteApiPutController extends ApiController{

    private final NoteRegister register;

    public NoteApiPutController(ModelMapper mapper, NoteRegister register) {
        super(mapper);
        this.register = register;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(RoutesConfig.API.NOTES)
    public void register(@RequestBody NoteRegistrationForm registrationForm) {
        register.register(registrationForm);
    }
}
