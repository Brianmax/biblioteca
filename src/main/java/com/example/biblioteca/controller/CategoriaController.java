package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.CategoriaCreateDto;
import com.example.biblioteca.dto.response.ApiResponse;
import com.example.biblioteca.dto.response.CategoriaResponseDto;
import com.example.biblioteca.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoriaResponseDto>> save(@Valid @RequestBody CategoriaCreateDto dto) {
        CategoriaResponseDto response = categoriaService.saveCategoria(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Categoría creada", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoriaResponseDto>> findById(@PathVariable UUID id) {
        CategoriaResponseDto response = categoriaService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Categoría encontrada", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoriaResponseDto>>> findAll() {
        List<CategoriaResponseDto> response = categoriaService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Categorías encontradas", response));
    }
}
