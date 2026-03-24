package com.example.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "libros")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibroEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String titulo;

    @Column(unique = true)
    private String isbn;

    private Double precio;

    private int stock;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @ManyToOne
    @JoinColumn(name = "autor_id_fk")
    private AutorEntity autor;

    @ManyToMany
    @JoinTable(
            name = "libro_categoria",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<CategoriaEntity> categorias;
}
