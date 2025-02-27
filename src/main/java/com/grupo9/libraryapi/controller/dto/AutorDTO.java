package com.grupo9.libraryapi.controller.dto;

import com.grupo9.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                                @NotBlank(message = "Campo obrigatório!")
                       @Size(min = 2, max = 100, message = "Digite um valor entre 2 e 100 caracteres!") String nome,
                                @NotNull(message = "Campo obrigatório!")
                       @Past(message = "Não é possível registrar uma data futura!") LocalDate dataNascimento,
                                @NotBlank(message = "Campo obrigatório!")
                       @Size(min = 2, max = 50, message = "Digite um valor entre 2 e 50 caracteres!") String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
