package com.example.notesapp.shared.domain.repositories;

import com.example.notesapp.shared.domain.Identifier;

import java.util.List;
import java.util.Optional;

public interface QueryRepository<ID extends Identifier, T> extends Repository<ID, T> {

    Optional<T> find(ID id);

    List<T> find();
}