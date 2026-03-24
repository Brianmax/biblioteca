package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.CategoriaCreateDto;
import com.example.biblioteca.dto.response.CategoriaResponseDto;
import com.example.biblioteca.entity.CategoriaEntity;
import com.example.biblioteca.exception.BusinessException;
import com.example.biblioteca.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    public CategoriaResponseDto saveCategoria(CategoriaCreateDto dto) {
        boolean nombreExiste = categoriaRepository.findByNombre(dto.getNombre()).isPresent();
        if (nombreExiste) {
            throw new BusinessException("Ya existe una categoría con el nombre: " + dto.getNombre());
        }

        CategoriaEntity categoria = new CategoriaEntity();
        modelMapper.map(dto, categoria);
        categoriaRepository.save(categoria);

        CategoriaResponseDto response = new CategoriaResponseDto();
        modelMapper.map(categoria, response);
        return response;
    }

    public CategoriaResponseDto findById(UUID id) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new com.example.biblioteca.exception.ResourceNotFoundException("Categoría no encontrada con id: " + id));

        CategoriaResponseDto response = new CategoriaResponseDto();
        modelMapper.map(categoria, response);
        return response;
    }

    public List<CategoriaResponseDto> findAll() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> {
                    CategoriaResponseDto dto = new CategoriaResponseDto();
                    modelMapper.map(categoria, dto);
                    return dto;
                }).toList();
    }
}
