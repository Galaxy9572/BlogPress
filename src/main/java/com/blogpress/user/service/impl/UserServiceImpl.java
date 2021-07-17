package com.blogpress.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.blogpress.common.UserConstants;
import com.blogpress.common.util.AssertUtils;
import com.blogpress.common.util.HashUtils;
import com.blogpress.common.util.bean.BeanCopyUtils;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.entity.User;
import com.blogpress.user.bean.response.UserVO;
import com.blogpress.user.dao.UserMapper;
import com.blogpress.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务实现类
 *
 * @author JY
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserVO register(HttpServletRequest request, UserDTO userDTO) {
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
            UserVO vo = new UserVO();
            BeanCopyUtils.copy(registeredUser, vo, true);
            addUserSession(request, vo);
            return vo;
        } else {
            log.error("Register Failed, Param: {}", JSON.toJSONString(userDTO));
            return null;
        }
    }

    @Override
    public UserVO login(HttpServletRequest request, UserDTO user) {
        UserVO userVO = getUserSession(request, user.getUserId());
        if (userVO != null) {
            return userVO;
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

        UserVO vo = new UserVO();
        BeanCopyUtils.copy(existUser, vo, true);
        addUserSession(request, vo);
        return vo;
    }

    /**
     * 获取用户session
     * @param request HttpServletRequest
     * @param userId  用户ID
     * @return UserVO
     */
    private static UserVO getUserSession(HttpServletRequest request, Long userId) {
        if (request == null || userId == null) {
            log.warn("Cannot get user session, request or userId is null");
            return null;
        }
        String userSessionKey = UserConstants.getUserSessionKey(userId);
        return (UserVO) request.getSession().getAttribute(userSessionKey);
    }

    /**
     * 添加用户session
     * @param request HttpServletRequest
     * @param userVO  UserVO
     */
    private static void addUserSession(HttpServletRequest request, UserVO userVO) {
        if (request == null || userVO == null) {
            log.warn("Cannot add user session, request or userVO is null");
            return;
        }
        String userSessionKey = UserConstants.getUserSessionKey(userVO.getUserId());
        request.getSession().setAttribute(userSessionKey, userVO);
    }

}
