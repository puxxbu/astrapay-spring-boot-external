package com.astrapay.repository;

import com.astrapay.entity.Note;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class NoteRepositoryImpl implements NoteRepository {

    private final Map<String, Note> notes = new ConcurrentHashMap<>();

    @Override
    public Note save(Note note) {
        if (note.getId() == null) {
            note.setId(UUID.randomUUID().toString());
            note.setCreatedAt(LocalDateTime.now());
        }
        note.setUpdatedAt(LocalDateTime.now());
        notes.put(note.getId(), note);
        return note;
    }

    @Override
    public List<Note> findAll() {
        return notes.values().stream()
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Note> findById(String id) {
        return Optional.ofNullable(notes.get(id));
    }

    @Override
    public void deleteById(String id) {
        notes.remove(id);
    }
}
