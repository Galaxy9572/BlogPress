package com.blogpress.user.service;

import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.response.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 * @author JY
 */
public interface IUserService {

    /**
     * 用户注册
     * @param request HttpServletRequest
     * @param user UserDTO
     * @return UserVO
     */
    UserVO register(HttpServletRequest request, UserDTO user);

    /**
     * 用户登录
     * @param request HttpServletRequest
     * @param user UserDTO
     * @return UserVO
     */
    UserVO login(HttpServletRequest request, UserDTO user);

}
