package com.example.biblioteca.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AutorDescuentoResponseDto {
    private UUID id;
    private String nombre;
    private String apellido;
    private int cantidadLibros;
    private double porcentajeDescuento;
}
