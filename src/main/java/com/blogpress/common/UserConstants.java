package com.blogpress.common;

import java.text.MessageFormat;

/**
 * 用户相关的常量
 * @author JY
 */
public class UserConstants {

    /** 用户登录注册后的session key */
    private static final String USER_SESSION_KEY = "user_session_{0}";

    /**
     * 获取用户session的key
     * @param userId 用户ID
     * @return 用户session的key
     */
    public static String getUserSessionKey(Long userId){
        return MessageFormat.format(USER_SESSION_KEY, userId);
    }

}
