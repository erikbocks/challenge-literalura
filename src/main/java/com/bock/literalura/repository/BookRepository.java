package com.bock.literalura.repository;

import com.bock.literalura.models.Book;
import com.bock.literalura.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitleContainingIgnoreCase(String title);

    @Query("select b.title from Book b where b.author.id = :authorId")
    List<String> findBookTitlesByAuthor(Long authorId);

    List<Book> findBookByLanguage(Language language);

    Optional<Book> findBookByTitle(String title);

    List<Book> findAllTop10ByOrderByDownloadCountDesc();
}
