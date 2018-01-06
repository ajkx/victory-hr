package com.victory.common.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/11/24.
 */
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
        //设置null转换""
        getSerializerProvider().setNullValueSerializer(new JsonNullSerializer());
        //设置日期转换yyyy-MM-dd HH:mm:ss
//        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SimpleModule module = new SimpleModule();
        // 配置全局反序列的Time 将 HH:mm 转为HH:mm:ss
        module.addDeserializer(Time.class, new JsonTimeDeserializer());
        // 配置全局的Time的序列化 即将 HH:mm:ss 转为 HH:mm
        module.addSerializer(Time.class, new JsonTimeSerializer());

        //配置全局的Boolean的序列化和反序列化 转为 0 和 1
        module.addDeserializer(Boolean.class, new JsonBooleanDeserializer());
        module.addSerializer(Boolean.class, new JsonBooleanSerializer());

        registerModule(module);
    }


}
