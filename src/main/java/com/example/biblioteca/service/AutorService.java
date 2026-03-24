package com.example.biblioteca.service;

import com.example.biblioteca.dto.request.AutorCreateDto;
import com.example.biblioteca.dto.response.AutorResponseDto;
import com.example.biblioteca.entity.AutorEntity;
import com.example.biblioteca.exception.BusinessException;
import com.example.biblioteca.exception.ResourceNotFoundException;
import com.example.biblioteca.repository.AutorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;

    public AutorService(AutorRepository autorRepository, ModelMapper modelMapper) {
        this.autorRepository = autorRepository;
        this.modelMapper = modelMapper;
    }

    public AutorResponseDto saveAutor(AutorCreateDto dto) {
        boolean emailExiste = autorRepository.findByEmail(dto.getEmail()).isPresent();
        if (emailExiste) {
            throw new BusinessException("Ya existe un autor con el email: " + dto.getEmail());
        }

        AutorEntity autor = new AutorEntity();
        modelMapper.map(dto, autor);
        autorRepository.save(autor);

        AutorResponseDto response = new AutorResponseDto();
        modelMapper.map(autor, response);
        return response;
    }

    public AutorResponseDto findById(UUID id) {
        AutorEntity autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id: " + id));

        AutorResponseDto response = new AutorResponseDto();
        modelMapper.map(autor, response);
        return response;
    }

    public List<AutorResponseDto> findAll() {
        return autorRepository.findAll().stream()
                .map(autor -> {
                    AutorResponseDto dto = new AutorResponseDto();
                    modelMapper.map(autor, dto);
                    return dto;
                }).toList();
    }
}
