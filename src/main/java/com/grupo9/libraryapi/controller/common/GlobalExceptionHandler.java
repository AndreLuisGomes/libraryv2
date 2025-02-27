package com.grupo9.libraryapi.controller.common;

import com.grupo9.libraryapi.controller.dto.ErroCampo;
import com.grupo9.libraryapi.controller.dto.ErroResposta;
import com.grupo9.libraryapi.exceptions.CampoInvalidoException;
import com.grupo9.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.grupo9.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Obtém a lista de erros de campo que ocorreram durante a validação do objeto
        List<FieldError> fieldErrors = e.getFieldErrors();

        // Converte os erros de validação (FieldError) para uma lista de objetos personalizados (ErroCampo)
        List<ErroCampo> listaErros = fieldErrors
                .stream() // Inicia um fluxo (stream) para processar a lista de erros de campo
                .map(field -> new ErroCampo(
                        field.getField(), // Obtém o nome do campo que causou o erro
                        field.getDefaultMessage() // Obtém a mensagem de erro padrão associada ao campo
                ))
                .collect(Collectors.toList()); // Coleta os resultados do fluxo em uma lista do tipo ErroCampo

        // Cria e retorna uma instância de ErroResposta contendo:
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), // O código de status HTTP 422 (Unprocessable Entity)
                "Erro Validação!", // Mensagem geral para indicar que ocorreu um erro de validação
                listaErros // Lista de erros detalhados (nome do campo e mensagem de erro)
        );
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleChangeSetPersisterNotFound(ChangeSetPersister.NotFoundException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(
            HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação!",
                List.of( new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAcessDeniedException(AccessDeniedException e){
        return new ErroResposta(
                HttpStatus.FORBIDDEN.value(), "Acesso Negado!", List.of()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados(RuntimeException e){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro na administração!", List.of());
    }
}
