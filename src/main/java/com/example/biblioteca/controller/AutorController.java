package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.AutorCreateDto;
import com.example.biblioteca.dto.response.ResponseApi;
import com.example.biblioteca.dto.response.AutorResponseDto;
import com.example.biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/autores")
public class AutorController implements AutorApi{

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }
    @PostMapping
    public ResponseEntity<ResponseApi<AutorResponseDto>> save(@Valid @RequestBody AutorCreateDto dto) {
        AutorResponseDto response = autorService.saveAutor(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseApi<>(true, "Autor creado", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<AutorResponseDto>> findById(@PathVariable UUID id) {
        AutorResponseDto response = autorService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseApi<>(true, "Autor encontrado", response));
    }

    @GetMapping
    public ResponseEntity<ResponseApi<List<AutorResponseDto>>> findAll() {
        List<AutorResponseDto> response = autorService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseApi<>(true, "Autores encontrados", response));
    }
}
