package com.astrapay.repository;

import com.astrapay.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    Note save(Note note);
    List<Note> findAll();
    Optional<Note> findById(String id);
    void deleteById(String id);
}
