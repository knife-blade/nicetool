package com.suchtool.nicetool.util.base;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

/**
 * hibernate validator的校验工具
 */
public class ValidateUtil {
    private static final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 校验对象
     * @param object 对象
     * @param <T> 泛型
     */
    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage()).append(";");
            }

            throw new ValidationException(validateError.toString());
        }
    }

    /**
     * 校验对象集合
     * @param objectCollection 对象集合
     * @param <T> 泛型
     */
    public static <T> void validate(Collection<T> objectCollection) {
        for (T t : objectCollection) {
            validate(t);
        }
    }

    /**
     * 通过组来校验实体类
     * @param object 对象
     * @param groups 组
     * @param <T> 泛型
     */
    public static <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                validateError.append(constraintViolation.getMessage()).append(";");
            }

            throw new ValidationException(validateError.toString());
        }
    }

    /**
     * 通过组来校验实体类集合
     * @param objectCollection 对象集合
     * @param groups 组
     * @param <T> 泛型
     */
    public static <T> void validate(Collection<T> objectCollection, Class<?>... groups) {
        for (T t : objectCollection) {
            validate(t, groups);
        }
    }
}
