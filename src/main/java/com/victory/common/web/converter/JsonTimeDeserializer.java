package com.victory.common.web.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Time;

/**
 * Created by ajkx
 * Date: 2017/5/13.
 * Time:11:34
 */
public class JsonTimeDeserializer extends JsonDeserializer<Time> {
    @Override
    public Time deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String time = jsonParser.getText();
        time += ":00";
        return Time.valueOf(time);
    }
}
