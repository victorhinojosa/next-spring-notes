package com.example.notesapp.notes.infrastructure.persistence.db.spring;

import com.example.notesapp.notes.infrastructure.persistence.db.mappings.NoteDatabaseMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaNoteRepository extends JpaRepository<NoteDatabaseMapping, String> {
}
