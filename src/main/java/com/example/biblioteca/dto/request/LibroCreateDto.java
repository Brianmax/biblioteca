package com.example.biblioteca.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LibroCreateDto {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 13, message = "El ISBN debe tener entre 10 y 13 caracteres")
    private String isbn;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @Min(value = 1000, message = "El año de publicación no es válido")
    @Max(value = 2026, message = "El año de publicación no puede ser futuro")
    private int anioPublicacion;

    @NotNull(message = "El autor es obligatorio")
    private UUID autorId;
}
