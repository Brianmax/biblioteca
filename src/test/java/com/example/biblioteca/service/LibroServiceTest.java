package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.LibroCreateDto;
import com.example.biblioteca.dto.response.LibroResponseDto;
import com.example.biblioteca.entity.AutorEntity;
import com.example.biblioteca.entity.LibroEntity;
import com.example.biblioteca.exception.BusinessException;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LibroServiceTest {
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private LibroRepository libroRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    @InjectMocks
    private LibroService libroService;

    private LibroCreateDto libroDto;
    private AutorEntity autor;
    private LibroEntity libroGuardado;

    @BeforeEach
    void setUp() {
        libroDto = new LibroCreateDto();
        libroDto.setTitulo("Clean Code");
        libroDto.setIsbn("9780132350884");
        libroDto.setPrecio(45.99);
        libroDto.setStock(10);
        libroDto.setAnioPublicacion(2008);
        libroDto.setAutorId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        autor = new AutorEntity();
        autor.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        autor.setNombre("Gabriel");
        autor.setApellido("García Márquez");
        autor.setEmail("gabriel.marquez@example.com");
        autor.setNacionalidad("Colombiana");

        libroGuardado = new LibroEntity();
        libroGuardado.setId(UUID.randomUUID()); // opcional, JPA normalmente lo genera
        libroGuardado.setTitulo(libroDto.getTitulo());
        libroGuardado.setIsbn(libroDto.getIsbn());
        libroGuardado.setPrecio(libroDto.getPrecio());
        libroGuardado.setStock(libroDto.getStock());
        libroGuardado.setAnioPublicacion(libroDto.getAnioPublicacion());
        libroGuardado.setAutor(autor);
    }



    @Test
    // happy path
    void saveLibro() {
        when(autorRepository.findById(libroDto.getAutorId())).thenReturn(Optional.of(autor));
        when(libroRepository.findByIsbn(libroDto.getIsbn())).thenReturn(Optional.empty());
        when(libroRepository.save(any(LibroEntity.class))).thenReturn(libroGuardado);

        LibroResponseDto resultado = libroService.saveLibro(libroDto);

        assertNotNull(resultado);
        assertEquals(resultado.getTitulo(), libroDto.getTitulo());
        assertEquals(resultado.getNombreAutor(), autor.getNombre() + " " + autor.getApellido());

    }

    // test cuando un isbn ya existe
    @Test
    void saveLibroIsbnDuplicado() {
        when(autorRepository.findById(libroDto.getAutorId())).thenReturn(Optional.of(autor));
        when(libroRepository.findByIsbn(libroDto.getIsbn())).thenReturn(Optional.of(libroGuardado));

        BusinessException ex = assertThrows(BusinessException.class, ()->libroService.saveLibro(libroDto));
        assertTrue(ex.getMessage().contains(libroDto.getIsbn()));
    }

    // test cuando el id del autor no existe


    @Test
    void findById() {
    }

    @Test
    void findByAutorId() {
    }

    @Test
    void calcularPrecioFinal() {
    }
}