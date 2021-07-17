package com.blogpress.user.bean.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户前端视图类
 * @author JY
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty(value = "用户ID", example = "123456789")
    private Long userId;

    /** 昵称 */
    @ApiModelProperty(value = "昵称", example = "nick")
    private String nick;

    /** 性别 */
    @ApiModelProperty(value = "性别", example = "M")
    private String gender;

    /** 生日 */
    @ApiModelProperty(value = "生日", example = "1995-01-01")
    private String birthday;

    /** 手机 */
    @ApiModelProperty(value = "手机", example = "12345678901")
    private String phone;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱", example = "12345@qq.com")
    private String email;

    /** 国家 */
    @ApiModelProperty(value = "国家", example = "中国")
    private String country;

    /** 省份 */
    @ApiModelProperty(value = "省份", example = "广东省")
    private String province;

    /** 城市 */
    @ApiModelProperty(value = "城市", example = "深圳市")
    private String city;

    /** 街道 */
    @ApiModelProperty(value = "街道", example = "西乡街道")
    private String street;

    /** 创建时间 */
    @ApiModelProperty(value = "用户注册时间", example = "2021-07-01 12:00:00")
    private String createTime;

    /** 更新时间 */
    @ApiModelProperty(value = "用户信息更新时间", example = "2021-07-01 12:00:00")
    private String updateTime;

}
