package com.grupo9.libraryapi.service;

import com.grupo9.libraryapi.controller.dto.AutorDTO;
import com.grupo9.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.repository.AutorRepository;
import com.grupo9.libraryapi.repository.LivroRepository;
import com.grupo9.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    // declaração da variável validadora do autor, repository e livro repository
    private final AutorValidator validator;
    private final AutorRepository repository;
    private final LivroRepository livroRepository;

    // Injeção de dependência via método atribuidor
//    public AutorService(AutorRepository repository,
//                        AutorValidator validator,
//                        LivroRepository livroRepository){
//        this.repository = repository;
//        this.validator = validator;
//        this.livroRepository = livroRepository;
//    }

    // Metodo para salvar um autor
    public Autor salvar(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    // Metodo para atualizar um autor
    public void atualizar(Autor autor) {
        if (autor.getId() == null) {
            throw new IllegalArgumentException(("Para atualizar é necessário que o autor já esteja salvo na base!"));
        }
        validator.validar(autor);
        repository.save(autor);
    }

    // Metodo para obter um autor por id
    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);
    }

    // Metodo para deletar um autor
    public void deletar(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é possível excluir um autor que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisarPorNomeENacionalidade(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if (nome != null) {
            return repository.findByNome(nome);
        }
        if (nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);

        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
