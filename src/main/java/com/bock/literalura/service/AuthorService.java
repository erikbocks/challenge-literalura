package com.bock.literalura.service;

import com.bock.literalura.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> findAuthorByName(String authorName);

    Author saveAuthor(Author author);

    List<Author> getAll();

    List<Author> findAuthorsLivingInYear(int year);
}
