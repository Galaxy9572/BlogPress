package com.blogpress.common.exception;

import com.blogpress.common.util.SpringBeanUtil;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

/**
 * @author JY
 */
public class BusinessException extends RuntimeException{

    private static final MessageSource MESSAGE_SOURCE = SpringBeanUtil.getBean(MessageSource.class);

    @Getter
    private final HttpStatus httpStatus;

    public BusinessException(String message) {
        super(message);
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
    }

    public BusinessException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static BusinessException of(String i18nCode) {
        String i18nMessage = MESSAGE_SOURCE.getMessage(i18nCode, null, LocaleContextHolder.getLocale());
        return new BusinessException(i18nMessage);
    }

    public static BusinessException of(HttpStatus httpStatus, String i18nCode) {
        String i18nMessage = MESSAGE_SOURCE.getMessage(i18nCode, null, LocaleContextHolder.getLocale());
        return new BusinessException(httpStatus, i18nMessage);
    }

}
