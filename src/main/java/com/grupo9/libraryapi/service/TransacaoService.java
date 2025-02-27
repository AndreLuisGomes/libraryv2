package com.grupo9.libraryapi.service;

import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.model.GeneroLivro;
import com.grupo9.libraryapi.model.Livro;
import com.grupo9.libraryapi.repository.AutorRepository;
import com.grupo9.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){
        /* salvando livro ->
        // repository.save(livro);

        // obtendo id do livro = livro.getId();
        //var id = livro.getId();

        // salvando foto do livro -> bucket na nuvem
        // buckerService.salvar(livro.getImagem, id + ".png");

        // atualizar o nome do arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png")
        */
    }


    // todos os metodos de transação devem ser publicos
    @Transactional
    public void atualizarSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("47e4f9f6-a742-44bb-875f-995fd24aba5c"))
                .orElse(null);
        livro.setDataPublicacao(LocalDate.of(2024, 12, 16));

        // não é necessário executar o metodo save para salvar uma modificação
        // livroRepository.save(livro);
    }

    @Transactional
    public void executar(){
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Raiane");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1999,10,20));

        autorRepository.saveAndFlush(autor);

        // salvar o livro

        Livro livro = new Livro();
        livro.setTitulo("O jogo da virada");
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setIsbn("1991920-24102149");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setDataPublicacao(LocalDate.of(2024,12,16));
        livro.setAutor(autor);

        livroRepository.save(livro);

        // verificar nome do autor

        if(autor.getNome().equals("Raiane")){
            throw new RuntimeException("Rollback!");
        }
    }
}
