package com.woyou.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.ReflectionUtil.java
 * <p>
 * Creation     : 2018/6/21
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class ReflectionUtil {
    public ReflectionUtil() {
    }

    public static <T> T getFieldValue(Object obj, String fieldName, boolean resolveParent) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        Object[] rs = getField(obj, fieldName, resolveParent);
        if (rs == null) {
            throw new NoSuchFieldException("field:" + fieldName);
        } else {
            Field field = (Field)rs[0];
            Object targetObj = rs[1];
            return (T) field.get(targetObj);
        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object val, boolean resolveParent) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        Object[] rs = getField(obj, fieldName, resolveParent);
        if (rs == null) {
            throw new NoSuchFieldException("field:" + fieldName);
        } else {
            Field field = (Field)rs[0];
            Object targetObj = rs[1];
            field.set(targetObj, val);
        }
    }

    private static Object[] getField(Object obj, String elFieldName, boolean resolveParent) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        if (obj == null) {
            return null;
        } else {
            String[] fieldNames = elFieldName.split("[.]");
            Object targetObj = obj;
            Class<?> targetClass = obj.getClass();
            Object val = null;
            int i = 0;
            Field field = null;
            Object[] rs = new Object[2];
            String[] var10 = fieldNames;
            int var11 = fieldNames.length;

            for(int var12 = 0; var12 < var11; ++var12) {
                String fName = var10[var12];
                ++i;
                field = getField_(targetClass, fName, resolveParent);
                field.setAccessible(true);
                rs[0] = field;
                rs[1] = targetObj;
                val = field.get(targetObj);
                if (val == null) {
                    if (i < fieldNames.length) {
                        throw new IllegalAccessException("can not getFieldValue as field '" + fName + "' value is null in '" + targetClass.getName() + "'");
                    }
                    break;
                }

                targetObj = val;
                targetClass = val.getClass();
            }

            return rs;
        }
    }

    public static Field getField_(Class<?> targetClass, String fieldName, boolean resolveParent) throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException {
        NoSuchFieldException noSuchFieldExceptionOccor = null;
        Field rsField = null;

        Field field;
        try {
            field = targetClass.getDeclaredField(fieldName);
            rsField = field;
            if (!resolveParent) {
                field.setAccessible(true);
                return field;
            }
        } catch (NoSuchFieldException var6) {
            noSuchFieldExceptionOccor = var6;
        }

        if (noSuchFieldExceptionOccor != null) {
            if (!resolveParent) {
                throw noSuchFieldExceptionOccor;
            }

            while(true) {
                targetClass = targetClass.getSuperclass();
                if (targetClass == null) {
                    break;
                }

                try {
                    field = targetClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (NoSuchFieldException var7) {
                    if (targetClass.getSuperclass() == null) {
                        throw var7;
                    }
                }
            }
        }

        return rsField;
    }

    public static Class<?> getGenericClass(Field f) {
        if (List.class.isAssignableFrom(f.getType())) {
            Type fc = f.getGenericType();
            if (fc instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)fc;
                Class genericClazz = (Class)pt.getActualTypeArguments()[0];
                return genericClazz;
            }
        }

        return null;
    }

    public static <T> T construct(Class<T> clazz, Class<?>[] paramTypes, Object[] params) {
        if (clazz == null) {
            return null;
        } else {
            try {
                Constructor<T> constructor = clazz.getDeclaredConstructor(paramTypes);
                constructor.setAccessible(true);
                return constructor.newInstance(params);
            } catch (Throwable var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public static boolean setField(Object target, Field field, Object val) {
        boolean ret = false;

        try {
            field.setAccessible(true);
            field.set(target, val);
            ret = true;
        } catch (Throwable var5) {
            var5.printStackTrace();
        }

        return ret;
    }

    public static <T> T getFieldValue(Object target, Field field) {
        try {
            if (field != null) {
                field.setAccessible(true);
                return (T) field.get(target);
            }
        } catch (Throwable var3) {
            var3.printStackTrace();
        }

        return null;
    }

    public static Class<?> rawType(Class<?> type) {
        if (type.equals(Boolean.class)) {
            return Boolean.TYPE;
        } else if (type.equals(Integer.class)) {
            return Integer.TYPE;
        } else if (type.equals(Float.class)) {
            return Float.TYPE;
        } else if (type.equals(Double.class)) {
            return Double.TYPE;
        } else if (type.equals(Short.class)) {
            return Short.TYPE;
        } else if (type.equals(Long.class)) {
            return Long.TYPE;
        } else if (type.equals(Byte.class)) {
            return Byte.TYPE;
        } else {
            return type.equals(Character.class) ? Character.TYPE : type;
        }
    }
}
