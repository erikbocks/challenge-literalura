package com.bock.literalura.service;

public interface JsonConverter {
    <T> T getData(String json, Class<T> desiredClass);
}
