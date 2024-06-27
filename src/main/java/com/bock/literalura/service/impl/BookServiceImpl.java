package com.bock.literalura.service.impl;

import com.bock.literalura.models.Book;
import com.bock.literalura.models.Language;
import com.bock.literalura.repository.BookRepository;
import com.bock.literalura.service.BookService;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveBook(Book book) {
        repository.saveAndFlush(book);
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        return repository.findBookByTitle(title);
    }

    @Override
    public Optional<Book> findBookByTitlePart(String title) {
        return repository.findBookByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public List<String> getBooksTitlesByAuthor(Long authorId) {
        return repository.findBookTitlesByAuthor(authorId);
    }

    @Override
    public List<Book> getBooksByLanguage(Language language) {
        return repository.findBookByLanguage(language);
    }

    @Override
    public List<Book> getTop10() {
        return repository.findAllTop10ByOrderByDownloadCountDesc();
    }
}
