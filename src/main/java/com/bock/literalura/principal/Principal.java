package com.bock.literalura.principal;

import com.bock.literalura.models.Author;
import com.bock.literalura.models.Book;
import com.bock.literalura.models.Language;
import com.bock.literalura.models.dto.BookDTO;
import com.bock.literalura.repository.AuthorRepository;
import com.bock.literalura.repository.BookRepository;
import com.bock.literalura.service.ApiRequester;
import com.bock.literalura.service.AuthorService;
import com.bock.literalura.service.BookService;
import com.bock.literalura.service.impl.AuthorServiceImpl;
import com.bock.literalura.service.impl.BookServiceImpl;
import com.bock.literalura.service.impl.JsonConverterImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;

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
                    3 - Listar autores registrados.
                    4 - Listar autores vivos em determinado ano.
                    5 - Listar livros por idioma.
                    6 - Listar top 10 livros mais baixados.
                    \s
                    0 - Sair
                    =====================================
                    Sua opção:""");

            try {
                option = reader.nextInt();
            } catch (InputMismatchException ex) {
                option = 0;
                System.out.println("Ocorreu um erro ao receber os dados.");
            }

            switch (option) {
                case 1:
                    searchBook();
                    break;
                case 2:
                    listBooks();
                    break;
                case 3:
                    listAuthors();
                    break;
                case 4:
                    listAuthorsAliveInYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 6:
                    listBookTop10();
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

        searchBookInGutendex(bookName);
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

        BookDTO bookDTO = converter.getData(bookNode.toString(), BookDTO.class);

        Optional<Book> dbBook = bookService.findBookByTitlePart(bookDTO.title());

        if (dbBook.isPresent()) {
            Book book = dbBook.get();
            if (book.getTitle().equalsIgnoreCase(bookDTO.title())) {
                System.out.println(book);
            } else {
                saveBook(bookDTO);
            }
        } else {
            saveBook(bookDTO);
        }
    }

    private void saveBook(BookDTO bookDTO) {
        ArrayNode authorNode = bookDTO.authors();

        if (authorNode.isEmpty()) {
            System.out.println("Não foi possível buscar as informações necessários sobre o autor.\nCancelando busca...");
            return;
        }

        Author author = converter.getData(bookDTO.authors().get(0).toString(), Author.class);

        Optional<Author> dbAuthor = authorService.findAuthorByName(author.getName());

        if (dbAuthor.isEmpty()) {
            author = authorService.saveAuthor(author);
        } else {
            author = dbAuthor.get();
        }

        String language = bookDTO.languages().get(0).toString();
        Language bookLanguage = Language.fromString(language.replace("\"", ""));

        Book book = new Book(bookDTO.title(), bookLanguage, bookDTO.downloads(), author);

        bookService.saveBook(book);
        System.out.println(book);
    }

    private void listBooks() {
        List<Book> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado até o momento.");
            return;
        }

        System.out.printf("Total de %d livros cadastrados.", books.size());
        books.forEach(System.out::println);
    }

    private void listAuthors() {
        List<Author> authors = authorService.getAll();

        if (authors.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }

        System.out.printf("Total de %d autores cadastrados.\n", authors.size());
        authors.forEach(a -> {
            List<String> authorBooks = bookService.getBooksTitlesByAuthor(a.getId());
            System.out.printf("""
                     \s
                     Autor: %s
                     Ano de Nascimento: %d
                     Ano de Falecimento: %d
                     Livros: %s
                     \s
                    """, a.getName(), a.getBirthYear(), a.getDeathYear(), authorBooks);
        });
    }

    private void listAuthorsAliveInYear() {
        try {
            System.out.println("Qual o ano que deseja pesquisar? Ex: 1999");
            int year = reader.nextInt();

            List<Author> authors = authorService.findAuthorsLivingInYear(year);

            if (authors.isEmpty()) {
                System.out.println("Nenhum autor encontrado para essa data.");
                return;
            }

            authors.forEach(a -> {
                List<String> authorBooks = bookService.getBooksTitlesByAuthor(a.getId());
                System.out.printf("""
                         \s
                         Autor: %s
                         Ano de Nascimento: %d
                         Ano de Falecimento: %d
                         Livros: %s
                         \s
                        """, a.getName(), a.getBirthYear(), a.getDeathYear(), authorBooks);
            });
        } catch (InputMismatchException ex) {
            System.out.println("Ocorreu um erro ao receber os dados.");
        }
    }

    private void listBooksByLanguage() {
        try {
            System.out.println("""
                    Digite o idioma que você gostaria de buscar
                    \s
                    en - Inglês
                    es - Espanhol
                    fr - Francês
                    pt - Português
                    it - Italiano
                    de - Alemão
                    """);

            reader.nextLine();
            String lang = reader.nextLine();
            Language language = Language.fromString(lang);

            List<Book> books = bookService.getBooksByLanguage(language);

            if (books.isEmpty()) {
                System.out.println("\nNenhum livro nesse idioma encontrado.\n");
            } else {
                System.out.printf("Quantidade de livros encontrados: %d\n", books.size());
                books.forEach(System.out::println);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void listBookTop10() {
        List<Book> books = bookService.getTop10();

        System.out.println("Aqui está o top 10 livros mais baixados do LiterAlura:\n");
        books.forEach(System.out::println);
    }
}
