package com.grupo9.libraryapi.controller.mappers;

import com.grupo9.libraryapi.controller.dto.AutorDTO;
import com.grupo9.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LivroMapper.class)
public interface AutorMapper {

    // Exemplo de mudança de nome de atributos
    // @Mapping(source = "nome", target = "nomeDTO")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);

}
