package com.bock.literalura.service.impl;

import com.bock.literalura.models.Author;
import com.bock.literalura.repository.AuthorRepository;
import com.bock.literalura.service.AuthorService;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Author> findAuthorByName(String authorName) {
        return repository.findByName(authorName);
    }

    @Override
    public Author saveAuthor(Author author) {
        return repository.saveAndFlush(author);
    }

    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }
}

