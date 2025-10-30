package com.astrapay.service;

import com.astrapay.dto.request.CreateNoteRequestDto;
import com.astrapay.dto.response.NoteDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.NoteNotFoundException;
import com.astrapay.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note("1","Test Title", "Test Note", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testGetAllNotes() {
        when(noteRepository.findAll()).thenReturn(Collections.singletonList(note));

        List<NoteDto> notes = noteService.getAllNotes();

        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Test Note", notes.get(0).getContent());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testCreateNote() {
        CreateNoteRequestDto requestDto = new CreateNoteRequestDto();
        requestDto.setContent("New Note");

        // Mock proses save
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> {
            Note noteToSave = invocation.getArgument(0);
            noteToSave.setId("new-id");
            noteToSave.setCreatedAt(LocalDateTime.now());
            noteToSave.setUpdatedAt(LocalDateTime.now());
            return noteToSave;
        });

        NoteDto createdNote = noteService.createNote(requestDto);

        assertNotNull(createdNote);
        assertEquals("new-id", createdNote.getId());
        assertEquals("New Note", createdNote.getContent());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testDeleteNote_Success() {
        when(noteRepository.findById("1")).thenReturn(Optional.of(note));
        doNothing().when(noteRepository).deleteById("1");

        assertDoesNotThrow(() -> noteService.deleteNote("1"));

        verify(noteRepository, times(1)).findById("1");
        verify(noteRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeleteNote_NotFound() {
        when(noteRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.deleteNote("2"));

        verify(noteRepository, times(1)).findById("2");
        verify(noteRepository, never()).deleteById(anyString());
    }
}
