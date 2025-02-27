package com.grupo9.libraryapi.validator;

import com.grupo9.libraryapi.exceptions.CampoInvalidoException;
import com.grupo9.libraryapi.exceptions.RegistroDuplicadoException;
import com.grupo9.libraryapi.model.Livro;
import com.grupo9.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro){
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }
        if(isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicação a partir de 2020 o preço é obrigatório!");
        }
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId) // mapeia apenas o id do livro
                .stream()
                .anyMatch(id -> !id.equals(livro.getId())); // retorna verdadeiro ou falso dependendo da combinação
    }

    private boolean isPrecoObrigatorioNulo(Livro livro){
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }
}
