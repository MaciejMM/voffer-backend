package com.example.freight.utlis;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static <T> T fromJson(final String json, final Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(final Object src) {
        return gson.toJson(src);
    }

    public static <T> T fromJson(final String json, final Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
