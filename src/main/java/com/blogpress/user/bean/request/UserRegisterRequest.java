package com.blogpress.user.bean.request;

import com.blogpress.common.validate.annotation.DateValid;
import com.blogpress.common.validate.annotation.FieldMatch;
import com.blogpress.common.validate.annotation.GenderValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 用户注册请求类
 * @author JY
 */
@Data
@ApiModel(description="用户注册入参" )
@FieldMatch(first = "password", second = "repeatPassword", message = "{password.not.consistent}")
public class UserRegisterRequest {

    @ApiModelProperty(value = "昵称", example="nick")
    @NotEmpty(message = "{nick.cannot.empty}")
    private String nick;

    @ApiModelProperty(value = "性别", example="M")
    @GenderValid(message = "{gender.invalid}")
    private String gender;

    @ApiModelProperty(value = "生日", example="1995-01-01")
    @DateValid(message = "{date.format.invalid}")
    private String birthday;

    @ApiModelProperty(value = "密码", example="abcd123456")
    @NotBlank(message = "{password.cannot.empty}")
    @Size(min = 6, max = 20, message = "{password.length.invalid}")
    private String password;

    @ApiModelProperty(value = "重复密码", example="abcd123456")
    @NotBlank(message = "{password.cannot.empty}")
    private String repeatPassword;

}
