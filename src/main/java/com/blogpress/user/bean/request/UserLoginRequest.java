package com.blogpress.user.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录请求类
 * @author JY
 */
@Data
@ApiModel(description="用户登录入参" )
public class UserLoginRequest {

    @ApiModelProperty(value = "用户ID", example="123456")
    private Long userId;

    @ApiModelProperty(value = "昵称", example="nick")
    @NotBlank(message = "{nick.cannot.empty}")
    private String nick;

    @ApiModelProperty(value = "密码", example="abcd123456")
    @NotBlank(message = "{password.cannot.empty}")
    private String password;

}
