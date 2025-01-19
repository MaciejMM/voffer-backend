package com.example.freight.utlis;


import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtil {

    private static final Gson gson = new Gson();

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
