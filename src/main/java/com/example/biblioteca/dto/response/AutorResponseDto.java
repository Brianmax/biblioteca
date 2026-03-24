package com.example.biblioteca.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AutorResponseDto {
    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String nacionalidad;
}
