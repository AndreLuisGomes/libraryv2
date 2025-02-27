package com.grupo9.libraryapi.controller.mappers;

import com.grupo9.libraryapi.controller.dto.CadastroLivroDTO;
import com.grupo9.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.grupo9.libraryapi.model.Livro;
import com.grupo9.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
@RequiredArgsConstructor
public abstract class LivroMapper {

    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
