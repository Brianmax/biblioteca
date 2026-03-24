package com.example.biblioteca.repository;

import com.example.biblioteca.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LibroRepository extends JpaRepository<LibroEntity, UUID> {

    @Query(value = "SELECT * FROM libros WHERE isbn = :isbn", nativeQuery = true)
    Optional<LibroEntity> findByIsbn(String isbn);

    @Query(value = "SELECT * FROM libros WHERE autor_id_fk = CAST(:autorId AS uuid)", nativeQuery = true)
    List<LibroEntity> findByAutorId(UUID autorId);
}
