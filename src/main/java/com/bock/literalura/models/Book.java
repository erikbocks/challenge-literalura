package com.bock.literalura.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    Author author;
    List<String> languages;
    Integer downloads;

    public Book() {
    }

    public Book(String title, Author author, List<String> languages, Integer downloads) {
        this.title = title;
        this.author = author;
        this.languages = languages;
        this.downloads = downloads;
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

    public List<String> getLanguage() {
        return languages;
    }

    public void setLanguage(List<String> languages) {
        this.languages = languages;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return String.format("""
                ------------- LIVRO ------------
                 Titulo: %s
                 Autor: %s
                 Idioma: %s
                 Número de Downloads: %d
                --------------------------------
                """, title, author.getName(), languages.toString(), downloads);
    }
}
