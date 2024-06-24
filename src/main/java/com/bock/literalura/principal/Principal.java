package com.bock.literalura.principal;

import com.bock.literalura.models.Author;
import com.bock.literalura.models.Book;
import com.bock.literalura.models.dto.LivroDto;
import com.bock.literalura.service.ApiRequester;
import com.bock.literalura.service.impl.JsonConverterImpl;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner reader = new Scanner(System.in);
    private final ApiRequester requester = new ApiRequester();
    private final JsonConverterImpl converter = new JsonConverterImpl();

    public void exibirMenu() {
        int option = -1;

        while (option != 0) {
            System.out.println("""
                    =====================================
                    Bem vindo ao LiterAlura do Bock!
                    \s
                    1 - Buscar livro por titulo.
                    \s
                    0 - Sair
                    =====================================
                    Sua opção:""");

            option = reader.nextInt();

            switch (option) {
                case 1:
                    searchBook();
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

        String response = requester.getData(bookName);
        JsonNode json = converter.getData(response, JsonNode.class);
        JsonNode bookNode = json.get("results").get(0);

        if (bookNode == null) {
            System.out.println("\nNão foi possível encontrar o livro digitado.");
            return;
        }

        LivroDto bookDto = converter.getData(bookNode.toString(), LivroDto.class);
        Author author = converter.getData( bookDto.authors().get(0).toString(), Author.class);

        List<String> languages = new ArrayList<>();

        bookDto.languages().forEach(l -> {
            languages.add(l.toString());
        });

        Book book = new Book(bookDto.title(), author, languages, bookDto.downloads());
        System.out.println(book);
    }
}
