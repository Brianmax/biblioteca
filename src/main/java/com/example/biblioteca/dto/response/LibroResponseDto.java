package com.example.biblioteca.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LibroResponseDto {
    private UUID id;
    private String titulo;
    private String isbn;
    private Double precio;
    private int stock;
    private int anioPublicacion;
    private String nombreAutor;
}
