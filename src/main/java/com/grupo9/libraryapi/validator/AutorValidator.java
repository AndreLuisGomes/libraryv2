package com.grupo9.libraryapi.validator;

import com.grupo9.libraryapi.exceptions.RegistroDuplicadoException;
import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository){
        this.repository = repository;
    }

    public void validar(Autor autor){
        if(existeAutorDuplicado(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }
    }

//    public void validarOptional(Optional<Autor> autor){
//        if(existeAutorDuplicadoOptional(autor)){
//            throw new RegistroDuplicadoException("Essas informações já foram cadastradas em outro autor!");
//        }
//    }

    public boolean existeAutorDuplicado(Autor autor){
        Optional<Autor> resultado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());

        if(resultado.isPresent()){
            return true;
        }

        return false;
    }

//    public boolean existeAutorDuplicadoOptional(Optional<Autor> autor){
//        Optional<Autor> resultado = repository.findByNomeAndDataNascimentoAndNacionalidade(
//                autor.get().getNome(), autor.get().getDataNascimento(), autor.get().getNacionalidade());
//
//        if(autor.get().getId() == null){
//            return resultado.isPresent();
//        }
//
//        return !autor.get().getId().equals(resultado.get().getId()) && resultado.isPresent();
//    }
}
