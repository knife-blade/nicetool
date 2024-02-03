package com.suchtool.niceutil.util.base;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BeanUtil {
    /**
     * 对象浅拷贝
     * @param source 源对象
     * @param target 目标对象对应的类
     * @return 目标对象
     * @param <T> 目标对象泛型
     */
    public static <T> T copy(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T t;
        try {
            t = target.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, t);
        return t;
    }

    /**
     * 对象浅拷贝
     * @param sources 源对象列表
     * @param target 目标对象对应的类
     * @return 目标对象
     * @param <T> 目标对象泛型
     */
    public static <T> List<T> copy(List<?> sources, Class<T> target) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }
        List<T> targets = new LinkedList<>();
        for (Object source : sources) {
            T t = null;
            try {
                t = target.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            BeanUtils.copyProperties(source, t);
            targets.add(t);
        }
        return targets;
    }

    /**
     * 对象深拷贝
     * @param source 源对象
     * @param target 目标对象对应的类
     * @return 目标对象
     * @param <T> 目标对象泛型
     */
    public static <T> T deepCopy(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        String json = JsonUtil.toJsonString(source);
        return JsonUtil.toObject(json, target);
    }

    /**
     * 对象深拷贝
     * （此处第二个参数必须用TypeReference，如果用Class<T> 会导致泛型擦除，最后返回的是个List<LinedHashMap>）
     * @param sources 源对象列表
     * @param typeReference 目标对象对应的类型
     * @return 目标对象
     * @param <T> 目标对象泛型
     */
    public static <T> List<T> deepCopy(List<?> sources, TypeReference<List<T>> typeReference) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }
        String json = JsonUtil.toJsonString(sources);
        return JsonUtil.toObjectList(json, typeReference);
    }
}