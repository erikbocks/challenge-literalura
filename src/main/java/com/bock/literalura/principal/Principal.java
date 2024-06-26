package com.bock.literalura.principal;

import com.bock.literalura.models.Author;
import com.bock.literalura.models.Book;
import com.bock.literalura.models.Language;
import com.bock.literalura.models.dto.LivroDto;
import com.bock.literalura.repository.AuthorRepository;
import com.bock.literalura.repository.BookRepository;
import com.bock.literalura.service.ApiRequester;
import com.bock.literalura.service.AuthorService;
import com.bock.literalura.service.BookService;
import com.bock.literalura.service.impl.AuthorServiceImpl;
import com.bock.literalura.service.impl.BookServiceImpl;
import com.bock.literalura.service.impl.JsonConverterImpl;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner reader = new Scanner(System.in);
    private final ApiRequester requester = new ApiRequester();
    private final JsonConverterImpl converter = new JsonConverterImpl();
    private final BookService bookService;
    private final AuthorService authorService;

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookService = new BookServiceImpl(bookRepository);
        this.authorService = new AuthorServiceImpl(authorRepository);
    }

    public void exibirMenu() {
        int option = -1;

        while (option != 0) {
            System.out.println("""
                    =====================================
                    Bem vindo ao LiterAlura do Bock!
                    \s
                    1 - Buscar livro por titulo.
                    2 - Listar livros cadastrados.
                    \s
                    0 - Sair
                    =====================================
                    Sua opção:""");

            option = reader.nextInt();

            switch (option) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listBooks();
                    break;
                default:
                    System.out.println("\nOpção inválida.\n");
            }
        }
    }

    private void searchBook() {
        System.out.println("\nDigite o nome do livro que você deseja buscar:");
        reader.nextLine();
        String bookName = reader.nextLine();

        Optional<Book> possibleBook = bookService.findBookByTitle(bookName);

        if (possibleBook.isPresent()) {
            System.out.println("Foi encontrado um livro no banco de dados que continha esse nome.");

            Book book = possibleBook.get();
            System.out.println(book);
        } else {
            searchBookInGutendex(bookName);
        }

    }

    private void searchBookInGutendex(String bookName) {
        System.out.println("Buscando livro no Gutendex...");
        String response = requester.getData(bookName);
        JsonNode json = converter.getData(response, JsonNode.class);

        JsonNode bookNode = json.get("results").get(0);
        if (bookNode == null) {
            System.out.println("\nNão foi possível encontrar o livro digitado.");
            return;
        }

        LivroDto bookDto = converter.getData(bookNode.toString(), LivroDto.class);
        Author author = converter.getData(bookDto.authors().get(0).toString(), Author.class);

        Optional<Author> dbAuthor = authorService.findAuthorByName(author.getName());

        if (dbAuthor.isEmpty()) {
            author = authorService.saveAuthor(author);
        } else {
            author = dbAuthor.get();
        }

        String language = bookDto.languages().get(0).toString();
        Language bookLanguage = Language.fromString(language.replace("\"", ""));

        Book book = new Book(bookDto.title(), bookLanguage, bookDto.downloads(), author);

        bookService.saveBook(book);
        System.out.println(book);
    }

    private void listBooks() {
        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado até o momento.");
            return;
        }

        books.forEach(System.out::println);
    }
}
