package com.astrapay.controller;

import com.astrapay.dto.request.CreateNoteRequestDto;
import com.astrapay.dto.response.BaseResponse;
import com.astrapay.dto.response.NoteDto;
import com.astrapay.service.NoteService;
import com.astrapay.util.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
@Api(value = "NoteController", tags = {"Simple Notes API"})
@Slf4j
@CrossOrigin(origins = "*")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    @ApiOperation(value = "Get all notes", response = BaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list")
    })
    public ResponseEntity<BaseResponse<List<NoteDto>>> getAllNotes() {
        log.info("GET /notes called");
        List<NoteDto> notes = noteService.getAllNotes();
        return ResponseHelper.success("Notes retrieved successfully", notes, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a new note", response = BaseResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created note"),
            @ApiResponse(code = 400, message = "Invalid input, content cannot be empty")
    })
    public ResponseEntity<BaseResponse<NoteDto>> createNote(@Valid @RequestBody CreateNoteRequestDto requestDto) {
        NoteDto createdNote = noteService.createNote(requestDto);
        return ResponseHelper.created("Note Successfully Added", createdNote);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a note by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted note"),
            @ApiResponse(code = 404, message = "Note not found")
    })
    public ResponseEntity<BaseResponse<String>> deleteNote(@PathVariable String id) {
        log.info("DELETE /notes/{} called", id);
        noteService.deleteNote(id);
        return ResponseHelper.success("Note deleted successfully", id, HttpStatus.OK);
    }
}