package com.grupo9.libraryapi.security;

import com.grupo9.libraryapi.model.Usuario;
import com.grupo9.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{

        Optional<Usuario> usuario = service.obterPorLogin(login);

        if(usuario.isEmpty()){
          throw new UsernameNotFoundException("Usuario não encontrado!");
        }

        return User.builder()
                .username(usuario.get().getLogin())
                .password(usuario.get().getSenha())
                .roles(usuario.get().getRoles().toArray(new String[usuario.get().getRoles().size()]))
                .build();
    }
}
