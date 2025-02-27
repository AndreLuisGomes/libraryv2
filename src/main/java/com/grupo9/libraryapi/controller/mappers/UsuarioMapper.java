package com.grupo9.libraryapi.controller.mappers;

import com.grupo9.libraryapi.controller.dto.UsuarioDTO;
import com.grupo9.libraryapi.model.Usuario;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDto(Usuario usuario);
}
