package com.maplink.desafio.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final String LOCAL_DATE_TIME = "dd/MM/yyyy HH:mm:ss";

    @Override
    public LocalDateTime deserialize(
            JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME);
        return LocalDateTime.parse(jsonParser.getText(), dateTimeFormatter);
    }
}
