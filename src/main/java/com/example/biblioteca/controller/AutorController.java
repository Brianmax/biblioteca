package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.AutorCreateDto;
import com.example.biblioteca.dto.response.ApiResponse;
import com.example.biblioteca.dto.response.AutorResponseDto;
import com.example.biblioteca.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AutorResponseDto>> save(@Valid @RequestBody AutorCreateDto dto) {
        AutorResponseDto response = autorService.saveAutor(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Autor creado", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AutorResponseDto>> findById(@PathVariable UUID id) {
        AutorResponseDto response = autorService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Autor encontrado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AutorResponseDto>>> findAll() {
        List<AutorResponseDto> response = autorService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Autores encontrados", response));
    }
}
