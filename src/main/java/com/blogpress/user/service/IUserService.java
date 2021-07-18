package com.blogpress.user.service;

import com.blogpress.user.bean.request.UserLoginRequest;
import com.blogpress.user.bean.request.UserRegisterRequest;
import com.blogpress.user.bean.response.UserVO;

/**
 * 用户服务
 * @author JY
 */
public interface IUserService {

    /**
     * 用户注册
     * @param request UserRegisterRequest
     * @return UserVO
     */
    UserVO register(UserRegisterRequest request);

    /**
     * 用户登录
     * @param request UserLoginRequest
     * @return UserVO
     */
    UserVO login(UserLoginRequest request);

}
