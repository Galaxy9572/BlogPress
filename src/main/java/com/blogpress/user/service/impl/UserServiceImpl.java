package com.blogpress.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.blogpress.common.enums.role.RoleEnum;
import com.blogpress.common.exception.BusinessException;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.common.util.HashUtils;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.converter.UserBeanConverter;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.entity.User;
import com.blogpress.user.bean.entity.UserRole;
import com.blogpress.user.bean.request.UserLoginRequest;
import com.blogpress.user.bean.request.UserRegisterRequest;
import com.blogpress.user.bean.response.UserVO;
import com.blogpress.user.dao.UserMapper;
import com.blogpress.user.dao.UserRoleMapper;
import com.blogpress.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 *
 * @author JY
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO register(UserRegisterRequest request) {
        // 注册前检查昵称是否已被注册
        User existUser = userMapper.getUserByNick(request.getNick());
        AssertUtils.isNull(existUser, "user.already.registered");

        // 用户表插入数据
        User user = new User();
        BeanCopyUtils.copy(request, user);
        String salt = HashUtils.randomUuid();
        String passwordHash = HashUtils.sha256(request.getPassword() + salt);
        user.setPasswordHash(passwordHash);
        user.setSalt(salt);
        int userCount = userMapper.insert(user);

        // 角色表插入数据
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setCode(RoleEnum.USER.getCode());
        int roleCount = userRoleMapper.insert(userRole);

        if (userCount > 0 && roleCount > 0) {
            Long userId = user.getUserId();
            User registeredUser = userMapper.selectById(userId);
            List<UserRole> roles = userRoleMapper.selectByUserId(userId);
            UserVO vo = UserBeanConverter.toUserVO(registeredUser, roles);
            UserDTO dto = UserBeanConverter.toUserDTO(registeredUser, roles);
            ContextHolder.addUserSession(dto);
            return vo;
        } else {
            log.error("Register Failed, Param: {}", JSON.toJSONString(request));
            throw BusinessException.of("user.register.failed");
        }
    }

    @Override
    public UserVO login(UserLoginRequest request) {
        UserDTO dto = ContextHolder.currentLoginUser();
        if (dto != null) {
            return UserBeanConverter.toUserVO(dto);
        }
        // 检查用户是否存在
        User existUser = userMapper.getUserByNick(request.getNick());
        AssertUtils.isNotNull(existUser, "user.not.exist");

        // 检查密码是否正确
        String salt = existUser.getSalt();
        String passwordHash = existUser.getPasswordHash();
        String password = request.getPassword();
        String loginPasswordHash = HashUtils.sha256(password + salt);
        AssertUtils.equal(loginPasswordHash, passwordHash, "nick.or.password.wrong");

        List<UserRole> roles = userRoleMapper.selectByUserId(existUser.getUserId());
        UserVO vo = UserBeanConverter.toUserVO(existUser, roles);
        dto = UserBeanConverter.toUserDTO(existUser, roles);
        ContextHolder.addUserSession(dto);
        return vo;
    }

}
