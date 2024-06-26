package com.bock.literalura.repository;

import com.bock.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleIgnoreCase(String title);
}
