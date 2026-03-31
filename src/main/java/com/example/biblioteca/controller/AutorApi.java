package com.example.biblioteca.controller;

import com.example.biblioteca.dto.request.AutorCreateDto;
import com.example.biblioteca.dto.response.AutorResponseDto;
import com.example.biblioteca.dto.response.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;
@Tag(name = "Autores Controller", description = "Gestion de autores")
public interface AutorApi {
    @Operation(summary = "Crear autor", description = "Registra un nuevo autor. El email debe de ser unico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor creado correctamente",
                    content = @Content(
                            schema = @Schema(implementation = AutorResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Datos invalidos o email duplicado",
                    content = @Content(
                            schema = @Schema(implementation = ResponseApi.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "success": false,
                                        "message": "Ya existe un autor con el email george@gmail.com"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping
    ResponseEntity<ResponseApi<AutorResponseDto>> save(@Valid @RequestBody AutorCreateDto dto);
    @GetMapping("/{id}")
    ResponseEntity<ResponseApi<AutorResponseDto>> findById(@PathVariable UUID id);
    @GetMapping
    ResponseEntity<ResponseApi<List<AutorResponseDto>>> findAll();
}
