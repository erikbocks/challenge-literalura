package com.bock.literalura.service;

import com.bock.literalura.models.Book;
import com.bock.literalura.models.Language;

import java.util.List;
import java.util.Optional;

public interface BookService {
    void saveBook(Book book);

    Optional<Book> findBookByTitle(String title);

    Optional<Book> findBookByTitlePart(String title);

    List<Book> getAllBooks();

    List<String> getBooksTitlesByAuthor(Long authorId);

    List<Book> getBooksByLanguage(Language language);

    List<Book> getTop10();
}
