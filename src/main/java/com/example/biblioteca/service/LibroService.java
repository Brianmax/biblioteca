package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.LibroCreateDto;
import com.example.biblioteca.dto.response.LibroPrecioResponseDto;
import com.example.biblioteca.dto.response.LibroResponseDto;
import com.example.biblioteca.entity.AutorEntity;
import com.example.biblioteca.entity.LibroEntity;
import com.example.biblioteca.exception.BusinessException;
import com.example.biblioteca.exception.ResourceNotFoundException;
import com.example.biblioteca.helper.DescuentoHelper;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;
    private final DescuentoHelper descuentoHelper;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository, ModelMapper modelMapper, DescuentoHelper descuentoHelper) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.modelMapper = modelMapper;
        this.descuentoHelper = descuentoHelper;
    }

    public LibroResponseDto saveLibro(LibroCreateDto dto) {
        AutorEntity autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + dto.getAutorId()));

        boolean isbnExiste = libroRepository.findByIsbn(dto.getIsbn()).isPresent();
        if (isbnExiste) {
            throw new BusinessException("Ya existe un libro con el ISBN: " + dto.getIsbn());
        }

        LibroEntity libro = new LibroEntity();
        modelMapper.map(dto, libro);
        libro.setAutor(autor);
        libroRepository.save(libro);

        LibroResponseDto response = new LibroResponseDto();
        modelMapper.map(libro, response);
        response.setNombreAutor(autor.getNombre() + " " + autor.getApellido());
        return response;
    }

    public LibroResponseDto findById(UUID id) {
        LibroEntity libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));

        LibroResponseDto response = new LibroResponseDto();
        modelMapper.map(libro, response);
        response.setNombreAutor(libro.getAutor().getNombre() + " " + libro.getAutor().getApellido());
        return response;
    }

    public List<LibroResponseDto> findByAutorId(UUID autorId) {
        autorRepository.findById(autorId)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + autorId));

        return libroRepository.findByAutorId(autorId).stream()
                .map(libro -> {
                    LibroResponseDto dto = new LibroResponseDto();
                    modelMapper.map(libro, dto);
                    dto.setNombreAutor(libro.getAutor().getNombre() + " " + libro.getAutor().getApellido());
                    return dto;
                }).toList();
    }

    public LibroPrecioResponseDto calcularPrecioFinal(UUID id) {
        Optional<LibroEntity> libroOptional = libroRepository.findById(id);
        if (libroOptional.isEmpty()) {
            throw new ResourceNotFoundException("Libro no encontrado con id: " + id);
        }
        LibroEntity libro = libroOptional.get();
        AutorEntity autor = libro.getAutor();

        // < 3 libros -> 0 por ciento
        // 3 a 5 libros -> 10 por ciento
        // 6 o mas -> 20 por ciento

        int cantidadLibros = autor.getLibros().size();
        double porcentaje = descuentoHelper.calcularPorcentajeDescuento(cantidadLibros);
        double precioFinal = descuentoHelper.aplicarDescuento(libro.getPrecio(), porcentaje);

        LibroPrecioResponseDto responseDto = new LibroPrecioResponseDto();
        responseDto.setId(libro.getId());
        responseDto.setTitulo(libro.getTitulo());
        responseDto.setNombreAutor(libro.getAutor().getNombre());
        responseDto.setPrecioOriginal(libro.getPrecio());
        responseDto.setPorcentajeDescuento(porcentaje);
        responseDto.setPrecioFinal(precioFinal);
        return responseDto;
    }
}
