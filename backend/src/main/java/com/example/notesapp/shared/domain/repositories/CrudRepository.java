package com.example.notesapp.shared.domain.repositories;

import com.example.notesapp.shared.domain.Identifier;

public interface CrudRepository<ID extends Identifier, T> extends QueryRepository<ID, T>{

    void save(T entity);

    void delete(ID id);

}