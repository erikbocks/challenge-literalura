package com.bock.literalura.service;

import com.bock.literalura.models.Author;
import com.bock.literalura.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);

    Optional<Book> findBookByTitle(String title);

    List<Book> getAllBooks();

    List<String> getBooksTitlesByAuthor(Author author);
}
