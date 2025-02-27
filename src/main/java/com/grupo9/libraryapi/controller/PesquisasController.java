package com.grupo9.libraryapi.controller;

import com.grupo9.libraryapi.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class PesquisasController {

    @GetMapping("/pesquisar")
    public String pesquisaPorLogin(){
        return "obterUsuarios";
    }
}
