package com.blogpress.user.service;

import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.response.UserVO;

/**
 * 用户服务
 * @author JY
 */
public interface IUserService {

    /**
     * 用户注册
     * @param user UserDTO
     * @return UserVO
     */
    UserVO register(UserDTO user);

    /**
     * 用户登录
     * @param user UserDTO
     * @return UserVO
     */
    UserVO login(UserDTO user);

}
