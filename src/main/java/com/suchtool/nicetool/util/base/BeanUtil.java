package com.suchtool.nicetool.util.base;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

public class BeanUtil {
    /**
     * 对象浅拷贝
     *
     * @param source 源对象
     * @param target 目标对象对应的类
     * @param <T>    目标对象泛型
     * @return 目标对象
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
     *
     * @param sources 源对象列表
     * @param target  目标对象对应的类
     * @param <T>     目标对象泛型
     * @return 目标对象
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
     * 对象深拷贝（通过json）
     *
     * @param source 源对象
     * @param target 目标对象对应的类
     * @param <T>    目标对象泛型
     * @return 目标对象
     */
    public static <T> T deepCopyByJson(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        String json = JsonUtil.toJsonString(source);
        return JsonUtil.toObject(json, target);
    }

    /**
     * 对象深拷贝（通过json）
     * （此处第二个参数必须用TypeReference，如果用Class<T>：会导致泛型擦除，从而返回个List<LinedHashMap>）
     *
     * @param source        源对象
     * @param typeReference 目标对象对应的类型
     * @param <T>           目标对象泛型
     * @return 目标对象
     */
    public static <T> T deepCopyByJson(Object source, TypeReference<T> typeReference) {
        if (source instanceof Collection) {
            Collection<?> collection = (Collection<?>) source;
            if (CollectionUtils.isEmpty(collection)) {
                return null;
            }
        } else if (source instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) source;
            if (CollectionUtils.isEmpty(map)) {
                return null;
            }
        } else {
            if (source == null) {
                return null;
            }
        }

        String json = JsonUtil.toJsonString(source);
        return JsonUtil.toObject(json, typeReference);
    }

    /**
     * 对象深拷贝（通过Serializable）
     *
     * @param source 源对象
     * @param target 目标对象对应的类
     * @param <T>    目标对象泛型
     * @return 目标对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopyBySerializable(Object source, Class<T> target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source object and target class must not be null.");
        }

        if (!(source instanceof Serializable)) {
            throw new IllegalArgumentException("Source object must implement Serializable.");
        }

        try {
            // 将源对象写入流中
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(source);

            // 从流中读取对象并转换为目标类的实例
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}