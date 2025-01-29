package com.example.notesapp.shared.infrastructure.persistence;

public interface DatabaseMapping<T> {

    T domainMap();
}