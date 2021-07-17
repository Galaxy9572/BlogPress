package com.blogpress.common.util.bean;

import com.blogpress.common.util.bean.converter.CommonConverter;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cglib.beans.BeanCopier;

import javax.validation.constraints.NotNull;

/**
 * Bean属性复制工具
 * @author JY
 */
public class BeanCopyUtils {

    /** BeanCopier缓存 */
    private static final Cache<String, BeanCopier> COPIER_CACHE = Caffeine.newBuilder().maximumSize(100).build();

    /**
     * 将源对象的属性值复制到目标对象
     * @param source 源对象
     * @param target 目标对象
     * @param <S> 源对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> void copy(@NotNull(message = "Source Can Not Null") S source, @NotNull T target, boolean useConverter){
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        String sourceClassName = sourceClass.getSimpleName();
        String targetClassName = targetClass.getSimpleName();
        String key = sourceClassName + targetClassName;
        BeanCopier beanCopier = COPIER_CACHE.get(key, (k) -> BeanCopier.create(sourceClass, targetClass, useConverter));
        if(beanCopier == null){
            throw new IllegalArgumentException("Can Not Get BeanCopier For Source Class: " + sourceClassName + "And Target Class: " + targetClassName);
        }
        beanCopier.copy(source, target, useConverter ? new CommonConverter() : null);
    }

    /**
     * 将源对象的属性值复制到目标对象
     * @param source 源对象
     * @param target 目标对象
     * @param <S> 源对象泛型
     * @param <T> 目标对象泛型
     */
    public static <S, T> void copy(@NotNull(message = "Source Can Not Null") S source, @NotNull T target){
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        String sourceClassName = sourceClass.getSimpleName();
        String targetClassName = targetClass.getSimpleName();
        String key = sourceClassName + targetClassName;
        BeanCopier beanCopier = COPIER_CACHE.get(key, (k) -> BeanCopier.create(sourceClass, targetClass, false));
        if(beanCopier == null){
            throw new IllegalArgumentException("Can Not Get BeanCopier For Source Class: " + sourceClassName + "And Target Class: " + targetClassName);
        }
        beanCopier.copy(source, target, null);
    }

}
