package com.bock.literalura.repository;

import com.bock.literalura.models.Author;
import com.bock.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleIgnoreCase(String title);

    @Query("select b.title from Book b inner join b.author a on a = :author")
    List<String> findBookTitlesByAuthor(Author author);
}
