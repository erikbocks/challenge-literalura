package com.bock.literalura.repository;

import com.bock.literalura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    @Query("select a from Author a where a.birthYear < :year and a.deathYear > :year")
    List<Author> findLivingAuthors(int year);
}
