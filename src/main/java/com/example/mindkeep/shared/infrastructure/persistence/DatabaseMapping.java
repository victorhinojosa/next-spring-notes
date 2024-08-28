package com.example.mindkeep.shared.infrastructure.persistence;

public interface DatabaseMapping<T> {

    T domainMap();
}