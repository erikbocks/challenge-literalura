package com.bock.literalura.models;

public enum Language {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    PORTUGUESE("pt"),
    ITALIAN("it"),
    FINNISH("fi"),
    GERMAN("de");

    private String languageCode;

    Language(String languageCode) {
        this.languageCode = languageCode;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.languageCode.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Nenhum idioma encontrado para a string fornecida: " + text);
    }
}
