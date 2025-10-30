package com.astrapay.service;

import com.astrapay.dto.request.CreateNoteRequestDto;
import com.astrapay.dto.response.NoteDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.NoteNotFoundException;
import com.astrapay.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDto> getAllNotes() {
        return noteRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public NoteDto createNote(CreateNoteRequestDto requestDto) {

        Note note = new Note();
        note.setContent(requestDto.getContent());
        note.setTitle(requestDto.getTitle());

        Note savedNote = noteRepository.save(note);
        log.info("Note created: {}", savedNote.getId());
        return convertToDto(savedNote);
    }


    public void deleteNote(String id) {
        noteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Note not found: {}", id);
                    return new NoteNotFoundException("Note not found with id: " + id);
                });

        noteRepository.deleteById(id);
        log.info("Note deleted: {}", id);
    }

    private NoteDto convertToDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}