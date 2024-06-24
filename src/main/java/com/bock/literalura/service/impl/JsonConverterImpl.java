package com.bock.literalura.service.impl;

import com.bock.literalura.service.JsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverterImpl implements JsonConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> desiredClass) {
        try {
            return mapper.readValue(json, desiredClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
