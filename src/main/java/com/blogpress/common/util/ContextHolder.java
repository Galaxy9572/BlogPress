package com.blogpress.common.util;

import com.blogpress.user.bean.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 上下文获取工具
 * @author JY
 */
@Slf4j
public class ContextHolder {

    /** 用户登录注册后的session key */
    public static final String USER_SESSION_KEY = "user_session";

    /**
     * 获取当前 HttpServletRequest
     * @return HttpServletRequest
     */
    public static HttpServletRequest currentHttpRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null){
            return null;
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取当前登录用户
     * @return UserDTO
     */
    public static UserDTO currentLoginUser() {
        HttpServletRequest request = currentHttpRequest();
        if (request == null) {
            log.warn("Cannot get user session, request is null");
            throw new IllegalArgumentException("HttpServletRequest is null");
        }
        return (UserDTO) request.getSession().getAttribute(USER_SESSION_KEY);
    }

    /**
     * 添加用户session
     * @param dto  UserDTO
     */
    public static void addUserSession(UserDTO dto) {
        HttpServletRequest request = currentHttpRequest();
        if (request == null || dto == null) {
            log.warn("Cannot add user session, request or dto is null");
            return;
        }
        request.getSession().setAttribute(USER_SESSION_KEY, dto);
    }

}
