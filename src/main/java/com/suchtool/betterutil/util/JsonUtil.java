package com.suchtool.betterutil.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Json工具
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper =
            ApplicationContextHolder.getContext().getBean(ObjectMapper.class);

    static {
        // 反序列化：JSON字段中有Java对象中没有的字段时不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 反序列化：不允许基本类型为null
        //objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        // 序列化：序列化BigDecimal时不使用科学计数法输出
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

        // 序列化：Java对象为空的字段不拼接JSON
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * 字符串转为对象
     * @param jsonString JSON字符串
     * @param cls 对象对应的类
     * @return 对象
     * @param <T> 对象的类型
     */
    public static <T> T toObject(String jsonString, Class<T> cls) {
        try {
            return objectMapper.readValue(jsonString, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转为对象
     * @param jsonString JSON字符串
     * @param typeReference 对象对应的类型
     * @return 对象
     * @param <T> 对象的类型
     */
    public static <T> T toObject(String jsonString, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转为对象
     * @param jsonString JSON字符串
     * @param javaType 对象对应的类型
     * @return 对象
     * @param <T> 对象的类型
     */
    public static <T> T toObject(String jsonString, JavaType javaType) {
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 字符串转为Map
     * @param jsonString json字符串
     * @return map
     */
    @SuppressWarnings("rawtypes")
    public static Map toMap(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转为JSON字符串
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转为对象列表
     * @param jsonString JSON字符串
     * @param typeReference 对象类型
     * @return 对象列表
     * @param <T> 对象泛型
     */
    public static <T> List<T> toObjectList(String jsonString, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转为JsonNode
     * @param jsonString JSON字符串
     * @return JsonNode对象
     */
    public static JsonNode toJsonNode(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
