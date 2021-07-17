package com.blogpress.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.ContextHolder;
import com.blogpress.common.util.HashUtils;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.converter.UserBeanConverter;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.entity.User;
import com.blogpress.user.bean.response.UserVO;
import com.blogpress.user.dao.UserMapper;
import com.blogpress.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 *
 * @author JY
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO register(UserDTO userDTO) {
        // 注册前检查昵称是否已被注册
        User existUser = userMapper.getUserByNick(userDTO.getNick());
        AssertUtils.isNull(existUser, "user.already.registered");

        User user = new User();
        BeanCopyUtils.copy(userDTO, user);
        String salt = HashUtils.randomUuid();
        String passwordHash = HashUtils.sha256(userDTO.getPassword() + salt);
        user.setPasswordHash(passwordHash);
        user.setSalt(salt);

        int count = userMapper.insert(user);

        if (count > 0) {
            Long userId = user.getUserId();
            User registeredUser = userMapper.selectById(userId);
            UserVO vo = UserBeanConverter.toUserVO(registeredUser);
            UserDTO dto = UserBeanConverter.toUserDTO(registeredUser);
            ContextHolder.addUserSession(dto);
            return vo;
        } else {
            log.error("Register Failed, Param: {}", JSON.toJSONString(userDTO));
            return null;
        }
    }

    @Override
    public UserVO login(UserDTO user) {
        UserDTO dto = ContextHolder.currentLoginUser();
        if (dto != null) {
            UserVO vo = new UserVO();
            BeanCopyUtils.copy(dto, vo, true);
            return vo;
        }
        // 检查用户是否存在
        User existUser = userMapper.getUserByNick(user.getNick());
        AssertUtils.isNotNull(existUser, "user.not.exist");

        // 检查密码是否正确
        String salt = existUser.getSalt();
        String passwordHash = existUser.getPasswordHash();
        String password = user.getPassword();
        String loginPasswordHash = HashUtils.sha256(password + salt);
        AssertUtils.equal(loginPasswordHash, passwordHash, "nick.or.password.wrong");

        UserVO vo = UserBeanConverter.toUserVO(existUser);
        dto = UserBeanConverter.toUserDTO(existUser);
        ContextHolder.addUserSession(dto);
        return vo;
    }

}
