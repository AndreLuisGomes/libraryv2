package com.grupo9.libraryapi.controller;

import com.grupo9.libraryapi.controller.dto.UsuarioDTO;
import com.grupo9.libraryapi.controller.mappers.UsuarioMapper;
import com.grupo9.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario  = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
