package com.product.objectmapper.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.product.objectmapper.deserializer.CustomJsonDeserializer;
import com.product.objectmapper.serializer.CustomJsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LocalDate.class, new CustomJsonSerializer.LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new CustomJsonDeserializer.LocalDateDeserializer());
        module.addSerializer(LocalDateTime.class, new CustomJsonSerializer.LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new CustomJsonDeserializer.LocalDateTimeDeserializer());
        module.addSerializer(LocalTime.class, new CustomJsonSerializer.LocalTimeSerializer());
        module.addDeserializer(LocalTime.class, new CustomJsonDeserializer.LocalTimeDeserializer());

        return new ObjectMapper().registerModule(module)
                                 .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

}
