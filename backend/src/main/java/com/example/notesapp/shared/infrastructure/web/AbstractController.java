package com.example.notesapp.shared.infrastructure.web;

import com.example.notesapp.shared.domain.errors.ForbiddenException;
import com.example.notesapp.shared.domain.errors.UnauthorizedException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractController<R> {

    protected final ModelMapper mapper;

    public AbstractController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public abstract R onNotFound();

    public abstract R onBadRequest(IllegalArgumentException ex);

    public abstract R onInternalServerError(Exception ex);

    public abstract R onUnauthorized(UnauthorizedException ex);

    public abstract R onForbidden(ForbiddenException ex);

    protected <S, T> List<T> map(List<S> source, Class<T> targetClass) {

        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}