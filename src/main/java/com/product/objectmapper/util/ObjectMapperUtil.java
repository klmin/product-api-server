package com.product.objectmapper.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.product.objectmapper.exception.JsonProcessingRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class ObjectMapperUtil {

    private final ObjectMapper objectMapper;
    private final ObjectMapper ignoreNullObjectMapper;

    public ObjectMapperUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.ignoreNullObjectMapper = objectMapper.copy();
        this.ignoreNullObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public String writeValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("[writeValueAsString Exception]", e);
            throw new JsonProcessingRuntimeException("Failed to convert object to JSON string", e);
        }
    }

    public <T> T readValue(String str, Class<T> clazz) {
        return Optional.ofNullable(str)
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, clazz);
                    } catch (IOException e) {
                        log.error("[readValue Exception]", e);
                        throw new JsonProcessingRuntimeException("Failed to convert JSON string to object", e);
                    }
                })
                .orElseThrow(
                        () -> new RuntimeException("Failed to convert JSON string to object: value is null or empty"));
    }

    public <T> T readValue(String str, TypeReference<T> typeRef) {
        return Optional.ofNullable(str)
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, typeRef);
                    } catch (IOException e) {
                        throw new JsonProcessingRuntimeException("Failed to convert JSON string to object", e);
                    }
                })
                .orElseThrow(
                        () -> new RuntimeException("Failed to convert JSON string to object: value is null or empty"));
    }

    public <T> T convertValue(Object obj, Class<T> type) {
        return ignoreNullObjectMapper.convertValue(obj, type);
    }

    public <T> T convertValue(Object obj, TypeReference<T> typeRef) {
        return ignoreNullObjectMapper.convertValue(obj, typeRef);
    }

}
