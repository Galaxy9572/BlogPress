package com.blogpress.common.util.bean.converter;

import com.blogpress.common.util.TimeUtils;
import org.springframework.cglib.core.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author JY
 */
public class CommonConverter implements Converter {

    @SuppressWarnings({"all"})
    @Override
    public Object convert(Object value, Class target, Object o1) {
        if(value == null) {
            return null;
        }
        Class<?> sourceClass = value.getClass();
        // 源数据是日期时间格式，转为LocalDateTime
        if (target.isAssignableFrom(LocalDateTime.class)
                && sourceClass.isAssignableFrom(String.class)
                && TimeUtils.isDateTimeFormat((String) value)) {
            return TimeUtils.toLocalDateTime((String) value);
        }
        // 源数据是日期格式，转为LocalDate
        if (target.isAssignableFrom(LocalDate.class)
                && sourceClass.isAssignableFrom(String.class)
                && TimeUtils.isDateFormat((String) value)) {
            return TimeUtils.toLocalDate((String) value);
        }
        // 源数据是LocalDateTime，转为yyyy-MM-dd HH:mm:ss格式
        if (target.isAssignableFrom(String.class)
                && sourceClass.isAssignableFrom(LocalDateTime.class)) {
            return TimeUtils.toDateTimeString((LocalDateTime) value);
        }
        // 源数据是LocalDate，转为yyyy-MM-dd格式
        if (target.isAssignableFrom(String.class)
                && sourceClass.isAssignableFrom(LocalDate.class)) {
            return TimeUtils.toDateString((LocalDate) value);
        }
        return value;
    }
}
