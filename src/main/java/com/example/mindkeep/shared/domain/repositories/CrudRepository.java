package com.example.mindkeep.shared.domain.repositories;

import com.example.mindkeep.shared.domain.Identifier;

public interface CrudRepository<ID extends Identifier, T> extends QueryRepository<ID, T>{

    void save(T entity);

    void delete(ID id);

}