package com.grupo9.libraryapi.repository;

import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.model.GeneroLivro;
import com.grupo9.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Raiane");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1999, 10, 20));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("8764203b-0d01-4eb4-8bf4-4b02bda262f5");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()) {

            Autor autorEncontrado = possivelAutor.get();

            System.out.println("Dados do autor:");
            System.out.println(possivelAutor.get());

            autorEncontrado.setNacionalidade("Brasileira");
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        Autor autor;
        List<Autor> autoresEncontrados= repository.findAll();
        autoresEncontrados.forEach(System.out::println);
    }

    @Test
    public void CountTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString( "8764203b-0d01-4eb4-8bf4-4b02bda262f5");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString( "e518b2fa-2039-464a-b14c-4bc164901e67");
        var marcos = repository.findById(id).get();
        repository.delete(marcos);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1970, 8,5));

        Livro livro = new Livro();
        livro.setTitulo("O roubo da casa assombrada");
        livro.setIsbn("99999-84874");
        livro.setAutor(autor);
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setDataPublicacao(LocalDate.of(1999,1,2));

        Livro livro2 = new Livro();
        livro2.setTitulo("Faz o L");
        livro2.setIsbn("20847-84874");
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setPreco(BigDecimal.valueOf(204));
        livro2.setDataPublicacao(LocalDate.of(2022,10,22));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    public void listarLivrosAutorTest(){
        var id = UUID.fromString("481929c9-9d74-440b-bcf9-6a69490e4ab8");
        var autor = repository.findById(id).get();

        List<Livro> livrosAutor = livroRepository.findByAutor(autor);
        autor.setLivros(livrosAutor);

        autor.getLivros().forEach(System.out::println);
    }


}
