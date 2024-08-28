package com.example.mindkeep.shared.infrastructure.web;

import com.example.mindkeep.config.ViewConfig;
import com.example.mindkeep.shared.domain.errors.ForbiddenException;
import com.example.mindkeep.shared.domain.errors.NotFoundException;
import com.example.mindkeep.shared.domain.errors.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public abstract class ViewController extends AbstractController<String> {

    public static final String APP_VERSION_MODEL_ATTRIBUTE = "appVersion";

    @Autowired
    private BuildProperties buildProperties;

    public ViewController(ModelMapper mapper) {
        super(mapper);
    }

    @Override
    @ExceptionHandler(NotFoundException.class)
    public String onNotFound() {
        return ViewConfig.RESOURCE_NOT_FOUND;
    }

    @Override
    @ExceptionHandler(IllegalArgumentException.class)
    public String onBadRequest(IllegalArgumentException ex) {
        return ViewConfig.BAD_REQUEST;
    }

    @Override
    @ExceptionHandler(UnauthorizedException.class)
    public String onUnauthorized(UnauthorizedException ex) {
        return ViewConfig.UNAUTHORIZED;
    }

    @Override
    @ExceptionHandler(ForbiddenException.class)
    public String onForbidden(ForbiddenException ex) {
        return ViewConfig.UNAUTHORIZED;
    }

    @Override
    @ExceptionHandler(Exception.class)
    public String onInternalServerError(Exception ex) {

        log.error("An unexpected error occurred", ex);
        return ViewConfig.INTERNAL_SERVER_ERROR;
    }

    protected void addMetadata(Model model) {
        String version = buildProperties == null ? "unknown" : buildProperties.getVersion();
        model.addAttribute(APP_VERSION_MODEL_ATTRIBUTE, version);
    }
}
