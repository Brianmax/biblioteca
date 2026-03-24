package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.LibroCreateDto;
import com.example.biblioteca.dto.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<LibroResponseDto>> save(@Valid @RequestBody LibroCreateDto dto) {
        LibroResponseDto response = libroService.saveLibro(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Libro creado", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LibroResponseDto>> findById(@PathVariable UUID id) {
        LibroResponseDto response = libroService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Libro encontrado", response));
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<ApiResponse<List<LibroResponseDto>>> findByAutor(@PathVariable UUID autorId) {
        List<LibroResponseDto> response = libroService.findByAutorId(autorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Libros encontrados", response));
    }
}
