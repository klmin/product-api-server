package com.product.objectmapper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.product.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomJsonDeserializer {

    private CustomJsonDeserializer(){

    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateUtil.dateFormatter("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateUtil.dateFormatter("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateUtil.dateFormatter("HH:mm");

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String dateString = jsonParser.getText();
            return LocalDate.parse(dateString, DATE_FORMATTER);
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String dateString = jsonParser.getText();
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String dateString = jsonParser.getText();
            return LocalTime.parse(dateString, TIME_FORMATTER);
        }
    }


}
