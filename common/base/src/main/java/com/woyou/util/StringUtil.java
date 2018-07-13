package com.woyou.util;

import android.text.TextUtils;

import com.woyou.util.format.Expose;
import com.woyou.util.format.SerializedName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ************************************************************
 * Copyright (C) 2005 - 2018 UCWeb Inc. All Rights Reserved
 * Description  :  cn.uc.gamesdk.background.util.StringUtil.java
 * <p>
 * Creation     : 2018/6/22
 * Author       : zhengxian.lzx@alibaba-inc.com
 * History      : Creation, 2018 lizx, Create the file
 * *************************************************************
 */
public class StringUtil {

    /**
     * 注解形式的对象属性变为 ` 符号分割的字符串
     * <p>
     * 注意：暂时没有实现数组
     *
     * @param bean
     * @return
     */
    public static String beanToText(Object bean) {
        StringBuilder sb = new StringBuilder();
        if (bean != null) {

            List<Field> fields = new ArrayList<Field>();
            Class<?> spClass = bean.getClass();
            while (spClass != null && spClass != Object.class) {
                fields.addAll(Arrays.asList(spClass.getDeclaredFields()));
                spClass = spClass.getSuperclass();
            }

            for (Field field : fields) {
                if (field.isAnnotationPresent(Expose.class)) {
                    SerializedName annoKey = field.getAnnotation(SerializedName.class);

                    String key;
                    if (annoKey == null) {
                        key = field.getName();
                    } else {
                        key = annoKey.value();
                    }

                    Object value = ReflectionUtil.getFieldValue(bean, field);
                    if (value == null) {
                        value = "null";
                    } else if (!isPrimitive(value.getClass())) {
                        value = beanToText(value);
                    }

                    sb.append(key).append("=").append(value).append("`");
                }
            }
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1); //删掉结尾多余的`符号
        }
        return sb.toString();
    }

    private static boolean isPrimitive(Class<?> clazz) {
        return ReflectionUtil.rawType(clazz).isPrimitive() || String.class.equals(clazz);
    }

    /**
     * dst与src部分匹配，匹配范围从src的start位置开始
     *
     * @param src   需要对比的源字段串
     * @param start 对比开始的位置（基于src,从0开始）
     * @param dst   需要对比的位置
     * @return 是否存在匹配
     */
    public static boolean match(String src, int start, String dst) {

        if (!(TextUtils.isEmpty(src) || TextUtils.isEmpty(dst))) {
            int end = start + dst.length();
            if (src.length() >= start && src.length() >= end) {
                char[] dstArray = dst.toCharArray();
                int i, j;
                for (i = start, j = 0; i < end && src.charAt(i) == dstArray[j]; i++, j++) {
                    ;
                }
                if (i == end) {
                    return true;
                }
            }
        }
        return false;
    }
}
