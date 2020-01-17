package com.camelot.kuka.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Json映射器
 * @author yingsonghao01
 * @version v1
 */
@Slf4j
@SuppressWarnings("unused")
public class JsonMapper {

    private ObjectMapper mapper;

    private JsonMapper() {}

    private JsonMapper(JsonInclude.Include include) {
        this.mapper = new ObjectMapper();
        if (include != null) {
            this.mapper.setSerializationInclusion(include);
        } else {
            this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    public static JsonMapper all() {
        return new JsonMapper(JsonInclude.Include.ALWAYS);
    }

    public static JsonMapper nonEmpty() {
        return new JsonMapper(JsonInclude.Include.NON_EMPTY);
    }

    public static JsonMapper def() {
        return new JsonMapper(null);
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("对象转化Json字符串异常：Object={}，exceptionMsg={}", object, e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Json字符串转化对象异常：jsonString={}，class={}，exceptionMsg={}", jsonString, clazz, e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.error("Json字符串转化对象异常：jsonString={}，javaType={}，exceptionMsg={}", jsonString, javaType.getTypeName(), e);
            return null;
        }
    }

    public JavaType buildCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public JavaType buildMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (IOException e) {
            log.error("更新Json字符串异常：jsonString={}，object={}，exceptionMsg={}", jsonString, object, e);
        }
    }

    public String findValueFromJson(String jsonString, String key) {
        String result = null;
        try {
            JsonMapper jsonMapper = JsonMapper.nonEmpty();
            JsonNode totalNode = jsonMapper.getMapper().readTree(jsonString);
            JsonNode keyNode = totalNode.findValue(key);
            if (keyNode != null) {
                result = keyNode.isContainerNode() ? keyNode.toString() : keyNode.asText();
            }
        } catch (Exception e) {
            log.error("Json解析异常：jsonString={}，key={}，exceptionMsg={}", jsonString, key, e);
        }
        return result;
    }

    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
