package com.bock.literalura.service.impl;

import com.bock.literalura.models.Book;
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
    public Book saveBook(Book book) {
        return repository.saveAndFlush(book);
    }

    @Override
    public Optional<Book> findBookByTitle(String title) {
        return repository.findByTitleIgnoreCase(title);
    }

    @Override
    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    @Override
    public List<String> getBooksTitlesByAuthor(Long authorId) {
        return repository.findBookTitlesByAuthor(authorId);
    }
}
