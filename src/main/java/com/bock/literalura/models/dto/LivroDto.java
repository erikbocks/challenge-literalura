package com.bock.literalura.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ArrayNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDto(String title, ArrayNode authors, ArrayNode languages, @JsonAlias("download_count") Integer downloads) {}
