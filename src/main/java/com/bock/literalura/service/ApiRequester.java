package com.bock.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequester {
    private final HttpClient client = HttpClient.newHttpClient();

    public String getData(String book) {
        String BASE_URL = "https://gutendex.com/books/?search=";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + book.replace(" ", "+").trim()))
                .build();
        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
}
