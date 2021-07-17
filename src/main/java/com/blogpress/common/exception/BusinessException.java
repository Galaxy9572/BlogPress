package com.blogpress.common.exception;

import com.blogpress.common.util.SpringBeanUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author JY
 */
public class BusinessException extends RuntimeException{

    private static final MessageSource MESSAGE_SOURCE = SpringBeanUtil.getBean(MessageSource.class);

    public BusinessException(String message) {
        super(message);
    }

    public static BusinessException of(String message) {
        String i18nMessage = MESSAGE_SOURCE.getMessage(message, null, LocaleContextHolder.getLocale());
        return new BusinessException(i18nMessage);
    }
}
