package com.example.biblioteca.repository;

import com.example.biblioteca.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, UUID> {

    @Query(value = "SELECT * FROM categorias WHERE nombre = :nombre", nativeQuery = true)
    Optional<CategoriaEntity> findByNombre(String nombre);
}
