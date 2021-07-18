package com.blogpress.user.bean.converter;

import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.dto.UserRoleDTO;
import com.blogpress.user.bean.entity.User;
import com.blogpress.user.bean.entity.UserRole;
import com.blogpress.user.bean.request.UserLoginRequest;
import com.blogpress.user.bean.request.UserRegisterRequest;
import com.blogpress.user.bean.response.UserRoleVO;
import com.blogpress.user.bean.response.UserVO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户Bean转换类
 *
 * @author JY
 */
public class UserBeanConverter {

    public static UserDTO toUserDTO(UserRegisterRequest request) {
        if (request == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanCopyUtils.copy(request, dto, true);
        return dto;
    }

    public static UserDTO toUserDTO(UserLoginRequest request) {
        if (request == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanCopyUtils.copy(request, dto, true);
        return dto;
    }

    public static UserDTO toUserDTO(User user, List<UserRole> roles) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanCopyUtils.copy(user, dto);

        if (!CollectionUtils.isEmpty(roles)) {
            List<UserRoleDTO> userRoleDTOList = roles.stream().map(UserBeanConverter::toUserRoleDTO).collect(Collectors.toList());
            dto.setRoles(userRoleDTOList);
        }

        return dto;
    }

    public static UserVO toUserVO(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopyUtils.copy(dto, userVO, true);

        List<UserRoleDTO> roles = dto.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            List<UserRoleVO> userRoleVOList = roles.stream().map(UserBeanConverter::toUserRoleVO).collect(Collectors.toList());
            userVO.setRoles(userRoleVOList);
        }

        return userVO;
    }

    public static UserVO toUserVO(User user, List<UserRole> roles) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopyUtils.copy(user, userVO, true);

        if (!CollectionUtils.isEmpty(roles)) {
            List<UserRoleVO> userRoleVOList = roles.stream().map(UserBeanConverter::toUserRoleVO).collect(Collectors.toList());
            userVO.setRoles(userRoleVOList);
        }

        return userVO;
    }

    public static UserRoleVO toUserRoleVO(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        UserRoleVO userRoleVO = new UserRoleVO();
        BeanCopyUtils.copy(userRole, userRoleVO, true);
        return userRoleVO;
    }

    public static UserRoleVO toUserRoleVO(UserRoleDTO userRole) {
        if (userRole == null) {
            return null;
        }
        UserRoleVO userRoleVO = new UserRoleVO();
        BeanCopyUtils.copy(userRole, userRoleVO, true);
        return userRoleVO;
    }

    public static UserRoleDTO toUserRoleDTO(UserRole userRole) {
        if (userRole == null) {
            return null;
        }
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        BeanCopyUtils.copy(userRole, userRoleDTO);
        return userRoleDTO;
    }

}
