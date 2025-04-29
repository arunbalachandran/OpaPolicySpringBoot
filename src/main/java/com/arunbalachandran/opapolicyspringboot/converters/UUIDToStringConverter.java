package com.arunbalachandran.opapolicyspringboot.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class UUIDToStringConverter implements Converter<UUID, String> {

    @Override
    public String convert(UUID source) {
        return source.toString();
    }
}
