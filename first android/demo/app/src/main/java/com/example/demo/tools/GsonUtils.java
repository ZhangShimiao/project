package com.example.demo.tools;

import android.text.TextUtils;

import com.google.gson.Gson;


public class GsonUtils<T> {
    // json字符串转换成对象
    //Convert JSON string to object
    public static <T> T json2Object(String json, Class<T> classOfT) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        //an object of type T from the string
        return gson.fromJson(json, classOfT);
    }

   // Convert JSON string to bean
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        Gson gson = new Gson();
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
}

