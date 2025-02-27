package com.grupo9.libraryapi.controller;

import com.grupo9.libraryapi.controller.dto.AutorDTO;
import com.grupo9.libraryapi.controller.mappers.AutorMapper;
import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorMapper autorMapper;
    private final AutorService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        service.salvar(autor);

        // http://localhost:8080/autores/{id}
        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole( 'OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> deletarAutor(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id); //  atribui um valor à variável idAutor
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) { // verifica se o autorOptional contem um objeto do tipo autor
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping // padrão DTO camada de representatividade
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        // pesquisando autores do tipo "autor"
        List<Autor> lista = service.pesquisaByExample(nome, nacionalidade);

        // convertendo objetos do tipo  "autor" para objetos do tipo "autorDTO"
        List<AutorDTO> resultado = lista
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                        )
                ).collect(Collectors.toList()); // <—  importante lembrar dessa estrutura aqui

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole( 'OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizarAutor(@PathVariable("id") String id, @RequestBody @Valid AutorDTO autorDTO) {
        var idAutor = UUID.fromString(id); // recebe o valor do parâmetro e atribui à variável "idAutor"
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        // recebe um autor opcional(caso exista ou não no banco) e atribui à uma variável "autorOptional"

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
            // caso o resultado da pesquisa para a "autor" seja nulo, devolve essa resposta
        }

        // setando novos valores advindos do autorDTO para o "autorResultado"
        var autorResultado = autorOptional.get();
        autorResultado.setId(autorOptional.get().getId());
        autorResultado.setNome(autorDTO.nome());
        autorResultado.setNacionalidade(autorDTO.nacionalidade());
        autorResultado.setDataNascimento(autorDTO.dataNascimento());
        System.out.println(autorResultado);

        service.atualizar(autorResultado); // atualiza com o novo "autorResultado"

        return ResponseEntity.noContent().build();
    }
}
