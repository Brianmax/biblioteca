package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.LibroCreateDto;
import com.example.biblioteca.dto.response.ResponseApi;
import com.example.biblioteca.dto.response.LibroResponseDto;
import com.example.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @PostMapping
    public ResponseEntity<ResponseApi<LibroResponseDto>> save(@Valid @RequestBody LibroCreateDto dto) {
        LibroResponseDto response = libroService.saveLibro(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseApi<>(true, "Libro creado", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<LibroResponseDto>> findById(@PathVariable UUID id) {
        LibroResponseDto response = libroService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseApi<>(true, "Libro encontrado", response));
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<ResponseApi<List<LibroResponseDto>>> findByAutor(@PathVariable UUID autorId) {
        List<LibroResponseDto> response = libroService.findByAutorId(autorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseApi<>(true, "Libros encontrados", response));
    }
}
