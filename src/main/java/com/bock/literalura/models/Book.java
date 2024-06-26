package com.bock.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Long id;
    @Column(name = "title")
    String title;
    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    Language language;
    @Column(name = "downloads")
    Integer downloadCount;
    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;

    public Book() {
    }

    public Book(String title, Language language, Integer downloads, Author author) {
        this.title = title;
        this.language = language;
        this.downloadCount = downloads;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloads) {
        this.downloadCount = downloads;
    }

    @Override
    public String toString() {
        return String.format("""
                \n------------- LIVRO ------------
                 Titulo: %s
                 Autor: %s
                 Idioma: %s
                 Número de Downloads: %d
                --------------------------------
                """, title, author.getName(), language, downloadCount);
    }
}
