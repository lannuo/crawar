package com.cskj.crawar.util.json;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json字符串转map
     *
     * @param str
     * @return
     */
    public static Map<String, Object> jsonStr2Map(String str)
            throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(str, Map.class);
    }

    /**
     * json字符串转实体
     *
     * @param str
     * @param cls
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T jsonStr2Entity(String str, Class cls)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) objectMapper.readValue(str, cls);
    }

    /**
     * json字符串转List<br>
     * 转换复杂类型Collection如 List，需要先反序列化复杂类型 为泛型的Collection Type
     *
     * @param str
     * @param beanCls Bean class
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T jsonStr2List(String str, Class beanCls)
            throws JsonParseException, JsonMappingException, IOException {
        return (T) objectMapper.readValue(str, getCollectionType(ArrayList.class, beanCls));
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 对象转json字符串
     *
     * @param obj entity，List...
     * @return
     * @throws JsonProcessingException
     */
    public static String object2JsonStr(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * 字符串转JSONObject<br>
     * 阿里fastjson
     *
     * @param str
     * @return
     */
    public static JSONObject jsonStr2JSON(String str) {
        return JSONObject.parseObject(str);
    }

    /**
     * 判断是否json字符串
     * @param jsonInString
     * @return
     */
    public static boolean isJSONValid(String jsonInString ) {
        try {
            objectMapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
