package com.blogpress.user.bean.converter;

import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.entity.User;
import com.blogpress.user.bean.request.UserLoginRequest;
import com.blogpress.user.bean.request.UserRegisterRequest;
import com.blogpress.user.bean.response.UserVO;

/**
 * 用户Bean转换类
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

    public static UserDTO toUserDTO(User user) {
        if(user == null){
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanCopyUtils.copy(user, dto);
        return dto;
    }

    public static UserVO toUserVO(UserDTO dto) {
        if(dto == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopyUtils.copy(dto, userVO, true);
        return userVO;
    }

    public static UserVO toUserVO(User user) {
        if(user == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopyUtils.copy(user, userVO, true);
        return userVO;
    }

}
