package com.woyou.util;

import android.text.TextUtils;
import android.util.Log;

import com.woyou.util.format.Expose;
import com.woyou.util.format.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by guosen.lgs@alibaba-inc.com on 12/29/16.
 */
public class JsonUtil {

    private static final String TAG = JsonUtil.class.getSimpleName();

    public static <T> T jsonToBean(String jsonStr, Class<T> clazz) {
        JSONObject jsonObject = buildJson(jsonStr);
        return jsonToBean(jsonObject, clazz);
    }

    public static <T> T jsonToBean(JSONObject jsonObj, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        T clazzObj = ReflectionUtil.construct(clazz, null, null);

        for (Field field : fields) {
            if (field.isAnnotationPresent(Expose.class)) {
                SerializedName annoKey = field.getAnnotation(SerializedName.class);

                String key;
                if (annoKey == null){
                    key = field.getName();
                }else {
                    key = annoKey.value();
                }

                Object value = jsonObj.opt(key);

                if (value instanceof JSONArray) {
                    //可能对不上，需要处理
                    value = jsonToBeanList((JSONArray) value, ReflectionUtil.getGenericClass(field));
                } else if (value instanceof JSONObject) {
                    if (String.class.equals(field.getType())) {
                        value = value.toString();
                    } else {
                        value = jsonToBean((JSONObject) value, field.getType());
                    }
                }
                ReflectionUtil.setField(clazzObj, field, value);
            }
        }
        return clazzObj;
    }

    public static <T> List<T> jsonToBeanList(JSONArray jsonArr, Class<T> clazz) {
        if (jsonArr != null) {
            T bean;
            int size = jsonArr.length();
            List<T> list = new ArrayList<T>(size);
            for (int i = 0; i < size; i++) {
                bean = (T) jsonArr.opt(0);
                if (!isPrimitive(clazz)) {
                    if (bean instanceof JSONObject) {
                        bean = (T) jsonToBean((JSONObject) bean, clazz);
                    } else if (bean instanceof JSONArray) {
                        bean = (T) jsonToBeanList((JSONArray) bean, clazz);
                    }
                }
                list.add(bean);
            }
            return list;
        }

        return new ArrayList<T>(0);
    }

    public static JSONArray beanListToJson(List object) {
        Iterator iterator = object.iterator();
        JSONArray jsonArray = new JSONArray();
        while (iterator.hasNext()) {
            Object bean = iterator.next();
            if (!isPrimitive(bean.getClass())) {
                bean = beanToJson(bean);
            }
            jsonArray.put(bean);
        }
        return jsonArray;
    }

    public static JSONObject beanToJson(Object bean) {
        JSONObject jsonObject = new JSONObject();
        if (bean != null) {

            List<Field> fields = new ArrayList<Field>();
            Class<?> spClass = bean.getClass();
            while (spClass != null && spClass != Object.class){
                fields.addAll(Arrays.asList(spClass.getDeclaredFields()));
                spClass = spClass.getSuperclass();
            }

            for (Field field : fields) {
                if (field.isAnnotationPresent(Expose.class)) {
                    SerializedName annoKey = field.getAnnotation(SerializedName.class);

                    String key;
                    if (annoKey == null){
                        key = field.getName();
                    }else {
                        key = annoKey.value();
                    }

                    Object value = ReflectionUtil.getFieldValue(bean, field);
                    if (value != null) {
                        if (value instanceof List) {
                            value = beanListToJson((List) value);
                        } else if (!isPrimitive(value.getClass())) {
                            value = beanToJson(value);
                        }
                    }
                    put(jsonObject, key, value);
                }
            }
        }

        return jsonObject;
    }

    public static JSONObject buildJson(String jsonStr) {
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject object = new JSONObject(jsonStr);
                return object;
            } catch (JSONException e) {
                Log.w(TAG, "buildJson",e);
            }
        }
        return new JSONObject();
    }

    private static void put(JSONObject jsonObj, String key, Object value) {
        try {
            jsonObj.put(key, value);
        } catch (JSONException e) {
            Log.w(TAG, "put", e);
        }
    }

    private static boolean isPrimitive(Class<?> clazz) {
        return ReflectionUtil.rawType(clazz).isPrimitive() || String.class.equals(clazz);
    }
}
