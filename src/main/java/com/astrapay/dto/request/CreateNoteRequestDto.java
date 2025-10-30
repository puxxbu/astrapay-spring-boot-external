package com.astrapay.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateNoteRequestDto {

    @NotEmpty(message = "Judul tidak boleh kosong")
    private String title;

    @NotEmpty(message = "Catatan tidak boleh kosong")
    private String content;
}
