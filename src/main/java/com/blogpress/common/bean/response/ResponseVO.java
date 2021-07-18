package com.blogpress.common.bean.response;

import com.blogpress.common.util.SpringBeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

/**
 * 返回结果类
 * @author JY
 */
@Data
@ApiModel(value = "返回响应数据", description= "返回响应数据")
public class ResponseVO<T> {

    /** 状态码 */
    @ApiModelProperty("状态码")
    private Integer code;

    /** 提示消息 */
    @ApiModelProperty("提示消息")
    private String message;

    /** 返回数据 */
    @ApiModelProperty("返回数据")
    private T data;

    public static <T> ResponseVO<T> success(String i18nCode){
        MessageSource messageSource = SpringBeanUtil.getBean(MessageSource.class);
        String i18nMessage = messageSource.getMessage(i18nCode, null, LocaleContextHolder.getLocale());
        return ResponseVO.build(HttpStatus.OK.value(), i18nMessage, null);
    }

    public static <T> ResponseVO<T> success(T data){
        return ResponseVO.build(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> ResponseVO<T> success(HttpStatus status, T data){
        return ResponseVO.build(status.value(), status.getReasonPhrase(), data);
    }

    public static <T> ResponseVO<T> success(String message, T data){
        return ResponseVO.build(HttpStatus.OK.value(), message, data);
    }

    public static <T> ResponseVO<T> failed(HttpStatus status, String message){
        return ResponseVO.build(status.value(), message, null);
    }

    public static <T> ResponseVO<T> failed(String i18nCode){
        MessageSource messageSource = SpringBeanUtil.getBean(MessageSource.class);
        String i18nMessage = messageSource.getMessage(i18nCode, null, LocaleContextHolder.getLocale());
        return ResponseVO.build(HttpStatus.EXPECTATION_FAILED.value(), i18nMessage, null);
    }

    public static <T> ResponseVO<T> failed(HttpStatus status, String i18nCode, Object... params){
        MessageSource messageSource = SpringBeanUtil.getBean(MessageSource.class);
        String i18nMessage = messageSource.getMessage(i18nCode, null, LocaleContextHolder.getLocale());
        String message = MessageFormat.format(i18nMessage, params);
        return ResponseVO.build(status.value(), message, null);
    }

    public static <T> ResponseVO<T> failed(HttpStatus status){
        return ResponseVO.build(status.value(), status.getReasonPhrase(), null);
    }

    private static <T> ResponseVO<T> build(Integer code, String message, T data) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode(code);
        responseVO.setMessage(message);
        responseVO.setData(data);
        return responseVO;
    }

}
