package com.victory.common.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Time;

/**
 * Created by Administrator on 2017/11/24.
 */
public class JsonBooleanSerializer extends JsonSerializer<Boolean>{
    @Override
    public void serialize(Boolean flag, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(flag ? 1 : 0);
    }
}
