package com.practice.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class JsonMain {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        /*objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);*/
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        SimpleModule jsr310Module = new JavaTimeModule();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        jsr310Module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        jsr310Module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        jsr310Module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        jsr310Module.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));

        objectMapper.registerModule(jsr310Module);

        MyValue myValue = new MyValue();
        myValue.stringArray = (new String[]{"a", "b"});
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        String json = objectMapper.writeValueAsString(myValue);
        System.out.println(json);

        String json2Object = "{\"intField\":0,\"longField\":0,\"doubleField\":0.0,\"floatField\":0.0,\"charField\":\"\\u0000\",\"booleanField\":false,\"stringArray\":[\"a\",\"b\"]}";
        MyValue a = objectMapper.readValue(json2Object, MyValue.class);
        Map<String, Object> map = objectMapper.convertValue(myValue, Map.class);
        System.out.println(map);

        System.out.println(objectMapper.writeValueAsString(null));

        System.out.println(Stream.of("a", "b").filter(s -> !s.equals("b")).map(String::toString).count());

        System.out.println(objectMapper.writeValueAsString(Arrays.asList("1", "2").toArray(new String[1])));


    }
}
