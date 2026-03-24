package com.example.biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String nombre;

    private String apellido;

    @Column(unique = true)
    private String email;

    private String nacionalidad;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<LibroEntity> libros;
}
