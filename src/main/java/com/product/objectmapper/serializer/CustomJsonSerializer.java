package com.product.objectmapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.product.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomJsonSerializer {

    private CustomJsonSerializer(){

    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateUtil.dateFormatter("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateUtil.dateFormatter("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateUtil.dateFormatter("HH:mm");

    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(DATE_FORMATTER.format(localDate));
        }
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(DATE_TIME_FORMATTER.format(localDate));
        }
    }

    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {

        @Override
        public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(TIME_FORMATTER.format(localTime));
        }
    }


}
