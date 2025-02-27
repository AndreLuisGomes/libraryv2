package com.grupo9.libraryapi.repository;

import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.model.GeneroLivro;
import com.grupo9.libraryapi.model.Livro;
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
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("938689-326257");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("A Terra do Nunca");
        livro.setDataPublicacao(LocalDate.of(2020, 10, 23));

        Autor autor = autorRepository.findById(UUID.fromString("d48926c8-336a-40ed-af50-2423fe8ec4f5")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    public void salvarAutorELivro() {
        Livro livro = new Livro();
        livro.setIsbn("465476-2123464");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Nosso verão");
        livro.setDataPublicacao(LocalDate.of(2024, 12, 15));

        Autor autor = new Autor();
        autor.setNome("André");
        autor.setDataNascimento(LocalDate.of(2002,2,4));
        autor.setNacionalidade("Brasileira");

        livro.setAutor(autor);
        autor.setLivros(new ArrayList<>());
        
        autor.getLivros().add(livro);

        autorRepository.save(autor);

    }

    @Test
    public void atualizarAutorDoLivro(){
    UUID id = UUID.fromString("10126ec9-d6d5-4a0b-b736-c2d8319cdea4");
    var livro = repository.findById(id).orElse(null);

    UUID autorId = UUID.fromString("f670cf44-b819-408e-b7a1-48989c8d6d79");
    Autor novoAutor = autorRepository.findById(autorId).orElse(null);

    livro.setAutor(novoAutor);

    repository.save(livro);
    }

    @Test
    public void deletarLivro(){
        UUID id = UUID.fromString("8b253766-e178-4e15-b51c-234e92a0221b");

        repository.deleteById(id);
    }

    @Test
    public void buscarLivroTest(){
        UUID id = UUID.fromString("");
        Livro livro = repository.findById(id).orElse(null);

        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){
        List<Livro> livros = repository.findByTitulo("AVATAR");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        Optional<Livro> livro = repository.findByIsbn("29385-235235");
        livro.ifPresent(System.out::println);
    }

    @Test
    void pesquisarPorTituloEPrecoTest(){
        var preco = BigDecimal.valueOf(100.00).setScale(2);
        var titulo = "UFO";
        List<Livro> livros = repository.findByTituloAndPreco(titulo, preco);

        livros.forEach(System.out::println);
    }

    @Test
    void buscarPorTituloOuIsbnTest(){
        // 29385-235235 , 4539846-346342 , UFO , AVATAR
        var titulo = "UFO";
        var isbn = "4539846-346342";
        List<Livro> livros = repository.findByTituloOrIsbn(titulo, isbn);

        livros.forEach(System.out::println);
    }

    @Test
    void procurarPorTitulo(){
        var titulo = "vata";

        List<Livro> livros = repository.findByTituloContaining(titulo);

        livros.forEach(System.out::println);
    }

    @Test
    void procurarPorAutorOrdenarPorData(){
        var titulo = "";
        var id = UUID.fromString("481929c9-9d74-440b-bcf9-6a69490e4ab8");
        Optional<Autor> autor = autorRepository.findById(id);
        List<Livro> livros = repository.findByAutorOrderByDataPublicacao(autor);

        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadosPorTituloEPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivrosComJPQL(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.FANTASIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryPositionalParametersTest(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.FANTASIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

//    @Test
//    void deletePorGeneroTest(){
//        repository.deleteByGenero();
//    }
}