package com.blogpress.user.bean.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色
 * @author JY
 */
@Data
public class UserRoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", example = "123456789")
    private Long userId;

    /** 角色编码 */
    @ApiModelProperty(value = "用户角色ID", example = "123456789")
    private String code;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", example = "2021-07-01")
    private String createTime;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", example = "2021-07-01")
    private String updateTime;

}
