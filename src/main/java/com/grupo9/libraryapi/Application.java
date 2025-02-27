package com.grupo9.libraryapi;

import com.grupo9.libraryapi.model.Autor;
import com.grupo9.libraryapi.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
// permite o funcionamento de auditorias, nesta  aplicacao habilita
// a anotação @EntityListener da classe autor
public class Application {

	public static void main(String[] args) {
		var context = SpringApplication.run(Application.class, args);

		AutorRepository repository = context.getBean(AutorRepository.class);

	}
}
