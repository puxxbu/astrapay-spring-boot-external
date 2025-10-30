package com.astrapay.controller;

import com.astrapay.dto.request.CreateNoteRequestDto;
import com.astrapay.dto.response.BaseResponse;
import com.astrapay.dto.response.NoteDto;
import com.astrapay.exception.NoteNotFoundException;
import com.astrapay.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {NoteController.class, com.astrapay.controller.advice.GlobalExceptionHandler.class})
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @Test
    void testGetAllNotes() throws Exception {
        NoteDto noteDto = new NoteDto("1", "Test Title", "Test Note", LocalDateTime.now(), LocalDateTime.now());
        when(noteService.getAllNotes()).thenReturn(Collections.singletonList(noteDto));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Notes retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].content").value("Test Note"));
    }

    @Test
    void testCreateNote_Success() throws Exception {
        CreateNoteRequestDto requestDto = new CreateNoteRequestDto();
        requestDto.setTitle("New Title");
        requestDto.setContent("New Note");

        NoteDto responseDto = new NoteDto("new-id", "New Title", "New Note", LocalDateTime.now(), LocalDateTime.now());
        when(noteService.createNote(any(CreateNoteRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Note Successfully Added"))
                .andExpect(jsonPath("$.data.id").value("new-id"))
                .andExpect(jsonPath("$.data.content").value("New Note"));
    }

    @Test
    void testCreateNote_ValidationFails() throws Exception {
        CreateNoteRequestDto requestDto = new CreateNoteRequestDto();
        requestDto.setContent("");
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.data.errors").exists());
    }


    @Test
    void testDeleteNote_Success() throws Exception {
        String noteIdToDelete = "1";
        doNothing().when(noteService).deleteNote(noteIdToDelete);

        mockMvc.perform(delete("/notes/" + noteIdToDelete))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Note deleted successfully"))
                .andExpect(jsonPath("$.data").value(noteIdToDelete));
    }

    @Test
    void testDeleteNote_NotFound() throws Exception {
        String nonExistentId = "2";
        doThrow(new NoteNotFoundException("Note not found")).when(noteService).deleteNote(nonExistentId);

        mockMvc.perform(delete("/notes/" + nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("ERROR"))
                .andExpect(jsonPath("$.message").value("Note not found"));
    }
}