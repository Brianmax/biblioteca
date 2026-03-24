package com.example.biblioteca.repository;

import com.example.biblioteca.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<AutorEntity, UUID> {

    @Query(value = "SELECT * FROM autores WHERE email = :email", nativeQuery = true)
    Optional<AutorEntity> findByEmail(String email);
}
