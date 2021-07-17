package com.blogpress.user.controller;

import com.blogpress.common.rest.bean.ResponseVO;
import com.blogpress.user.bean.converter.UserBeanConverter;
import com.blogpress.user.bean.dto.UserDTO;
import com.blogpress.user.bean.request.UserLoginRequest;
import com.blogpress.user.bean.request.UserRegisterRequest;
import com.blogpress.user.bean.response.UserVO;
import com.blogpress.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author JY
 */
@Slf4j
@Api(value = "用户操作相关API", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "注册接口", httpMethod = "POST")
    @ApiResponse(code = 200, message = "OK", response = UserVO.class)
    public ResponseVO<UserVO> register(@ApiParam(name = "用户注册入参", required = true) @Valid @NotNull
        @RequestBody UserRegisterRequest registerRequest, HttpServletRequest request) {

        UserDTO userDTO = UserBeanConverter.toUserDTO(registerRequest);
        UserVO userVO = iUserService.register(request, userDTO);
        return ResponseVO.success(userVO);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "登录接口", httpMethod = "POST")
    @ApiResponse(code = 200, message = "OK", response = UserVO.class)
    public ResponseVO<UserVO> login(@ApiParam(name = "用户登录入参", required = true) @Valid @NotNull
        @RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {

        UserDTO userDTO = UserBeanConverter.toUserDTO(loginRequest);
        UserVO userVO = iUserService.login(request, userDTO);
        return ResponseVO.success(userVO);
    }

}
