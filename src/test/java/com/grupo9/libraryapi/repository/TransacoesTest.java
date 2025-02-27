package com.grupo9.libraryapi.repository;

import com.grupo9.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest{

    @Autowired
    TransacaoService transacao;

    /**
     *
     * Esquema de transações com o EntityManager
     *
     * find -> busca objetos/dados de um banco de dados e torna a entidade managed
     *
     *
     * begin (comando sql) -> inicia a transação em sql
     * Commit -> confirma alterações
     * RollBack -> desfaz alterações
     */
    @Test
    void transacaoSimples(){
        transacao.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacao.atualizarSemAtualizar();
    }

}
