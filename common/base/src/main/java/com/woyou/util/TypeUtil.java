package com.woyou.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by heweile on 5/24/16.
 */
public class TypeUtil {

    private static final Set<Class<?>> PRIMITIVE_TYPES = new HashSet<Class<?>>();

    static {
        PRIMITIVE_TYPES.add(int.class);
        PRIMITIVE_TYPES.add(Integer.class);

        PRIMITIVE_TYPES.add(short.class);
        PRIMITIVE_TYPES.add(Short.class);

        PRIMITIVE_TYPES.add(long.class);
        PRIMITIVE_TYPES.add(Long.class);

        PRIMITIVE_TYPES.add(float.class);
        PRIMITIVE_TYPES.add(Float.class);

        PRIMITIVE_TYPES.add(double.class);
        PRIMITIVE_TYPES.add(Double.class);

        PRIMITIVE_TYPES.add(byte.class);
        PRIMITIVE_TYPES.add(Byte.class);

        PRIMITIVE_TYPES.add(boolean.class);
        PRIMITIVE_TYPES.add(Boolean.class);

        PRIMITIVE_TYPES.add(byte.class);
        PRIMITIVE_TYPES.add(Byte.class);

        PRIMITIVE_TYPES.add(char.class);
        PRIMITIVE_TYPES.add(Character.class);
    }

    public static boolean isPrimitive(Class<?> target) {
        return PRIMITIVE_TYPES.contains(target);
    }

    public static boolean isPrimitiveOrString(Class<?> target) {
        if (String.class.isAssignableFrom(target)) {
            return true;
        }

        return isPrimitive(target);
    }

    public static boolean isArrayOrContainer(Class<?> target) {
        if (target.isArray()) {
            return true;
        }

        if (List.class.isAssignableFrom(target) || Map.class.isAssignableFrom(target)) {
            return true;
        }

        return false;
    }

    public static boolean isAssignable(Class<?> target, Class<?> source) {
        if (target == Object.class || target.isAssignableFrom(source)) {
            return true;
        }

        return (TypeUtil.isPrimitive(target) && TypeUtil.isPrimitive(source));
    }

}
