package com.example.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String nombre;

    private String descripcion;

    @ManyToMany(mappedBy = "categorias")
    private List<LibroEntity> libros;
}
