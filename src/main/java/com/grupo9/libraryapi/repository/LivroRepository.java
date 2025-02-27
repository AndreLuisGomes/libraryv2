package com.grupo9.libraryapi.repository;

import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.model.GeneroLivro;
import com.grupo9.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    // Query Methods

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String  titulo, BigDecimal preco);

    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    List<Livro> findByTituloContaining(String titulo);

    List<Livro> findByAutorOrderByDataPublicacao(Optional<Autor> autor);

    // JPQL -> referencia as entidades e as propriedades

    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadosPorTituloEPreco();

    @Query("select autor from Livro l join l.autor")
    List<Autor> listarAutoresDosLivros();

    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l
            join l.autor a
            where a.nacionalidade = 'Brasileira'
            order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero,
                             @Param("paramOrdenacao") String nomePropriedade);

    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(@Param("genero") GeneroLivro genero,
                             @Param("paramOrdenacao") String nomePropriedade);

    // Indica que a operação é uma modificação no banco de dados (como DELETE, UPDATE, etc.)
    @Modifying
    // Garante que a operação será executada dentro de uma transação, garantindo consistência no banco de dados
    @Transactional
    // Define uma consulta personalizada que deleta registros da tabela Livro
    // A consulta deleta todos os livros cujo gênero seja igual ao valor passado como parâmetro
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero); // O método recebe o parâmetro 'genero' que será usado na consulta


    boolean existsByAutor(Autor autor);
}
