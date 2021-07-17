package com.blogpress.user.bean.converter;

import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.request.UserLoginRequest;
import com.blogpress.user.bean.request.UserRegisterRequest;

/**
 * User各对象转换类
 * @author JY
 */
public class UserBeanConverter {

    public static UserDTO toUserDTO(UserRegisterRequest request) {
        if(request == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanCopyUtils.copy(request, dto, true);
        return dto;
    }

    public static UserDTO toUserDTO(UserLoginRequest request) {
        if(request == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanCopyUtils.copy(request, dto, true);
        return dto;
    }

}
