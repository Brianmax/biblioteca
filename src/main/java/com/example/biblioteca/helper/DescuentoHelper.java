package com.example.biblioteca.helper;

import org.springframework.stereotype.Component;

@Component
public class DescuentoHelper {

    public double calcularPorcentajeDescuento(int cantidadLibros) {
        if (cantidadLibros >= 6) return 20.0;
        if (cantidadLibros >= 3) return 10.0;
        return 0.0;
    }

    public double aplicarDescuento(double precio, double porcentajeDescuento) {
        return precio * (1.0 - porcentajeDescuento / 100.0);
    }
}
