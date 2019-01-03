package com.cms.yhadmin.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapConvertUtil {
    /**
     * 把对象转化为map集合形式
     *
     * @param obj 需要转化的对象
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> obejctToMap(Object obj) {
        try {
            Map<String, Object> map = new HashMap<>();
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                map.put(fieldName, value);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把对象转化为map集合形式
     *
     * @param map   需要转化的对象
     * @param clazz 需要转化的对象
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            if (map == null)
                return null;

            T obj = clazz.newInstance();

            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    Object value = map.get(property.getName());
                    if (Objects.isNull(value)) {
                        continue;
                    }
                    setter.invoke(obj, value);
                }
            }

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
