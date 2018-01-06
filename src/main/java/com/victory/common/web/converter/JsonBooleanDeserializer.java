package com.victory.common.web.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Administrator on 2017/11/27.
 */
public class JsonBooleanDeserializer extends JsonDeserializer<Boolean>{
    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        int flag = jsonParser.getIntValue();
        return flag == 1 ? true : false;
    }
}
