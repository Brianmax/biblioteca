package com.example.biblioteca.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LibroPrecioResponseDto {
    private UUID id;
    private String titulo;
    private String nombreAutor;
    private double precioOriginal;
    private double porcentajeDescuento;
    private double precioFinal;
}
