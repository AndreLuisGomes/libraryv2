package com.grupo9.libraryapi.service;

import com.grupo9.libraryapi.model.Usuario;
import com.grupo9.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));

        repository.save(usuario);
    }

    public Optional<Usuario> obterPorLogin(String login){
        return repository.findByLogin(login);
    }
}
