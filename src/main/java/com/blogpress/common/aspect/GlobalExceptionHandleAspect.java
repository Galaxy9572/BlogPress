package com.blogpress.common.aspect;

import com.blogpress.common.exception.BusinessException;
import com.blogpress.common.rest.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理类
 * @author JY
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandleAspect {

    /**
     * 处理校验异常
     * @param e MethodArgumentNotValidException
     * @return ResponseVO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO<String> handleValidationException(MethodArgumentNotValidException e){
        StringBuilder builder = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        allErrors.forEach(error -> builder.append(error.getDefaultMessage()));
        return ResponseVO.failed(builder.toString());
    }

    /**
     * 处理业务异常
     * @param e BusinessException
     * @return ResponseVO
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseVO<String> handleBusinessException(BusinessException e){
        return ResponseVO.failed(e.getHttpStatus(), e.getMessage());
    }

    /**
     * 处理其它异常
     * @param e Exception
     * @return ResponseVO
     */
    @ExceptionHandler(Exception.class)
    public ResponseVO<String> handleOtherException(Exception e){
        log.error("Global Exception Handler Caught Exception", e);
        return ResponseVO.failed(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
