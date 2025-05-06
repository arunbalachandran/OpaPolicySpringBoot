package com.arunbalachandran.opapolicyspringboot.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.UUID;

@Component
@ReadingConverter
public class BytesToUUIDConverter implements Converter<byte[], UUID> {

    @Override
    public UUID convert(byte[] source) {
        ByteBuffer buffer = ByteBuffer.wrap(source);
        long mostSignficantBits = buffer.getLong();
        long leastSignificantBits = buffer.getLong();
        return new UUID(mostSignficantBits, leastSignificantBits);
    }
}
