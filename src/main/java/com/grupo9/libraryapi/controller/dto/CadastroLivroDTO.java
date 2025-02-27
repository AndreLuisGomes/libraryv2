package com.grupo9.libraryapi.controller.dto;

import com.grupo9.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN(type = ISBN.Type.ANY)
        @NotBlank(message = "Campo obrigatório!")
        String isbn,
        @NotBlank(message = "Campo obrigatório!")
        String titulo,
        @NotNull(message = "Campo obrigatório!")
        @Past(message = "Não pode ser uma data futura!")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "Campo obrigatório!")
        UUID idAutor
        ) {
}
