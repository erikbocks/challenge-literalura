package com.bock.literalura;

import com.bock.literalura.principal.Principal;
import com.bock.literalura.repository.AuthorRepository;
import com.bock.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(bookRepository, authorRepository);
		principal.exibirMenu();
	}
}
