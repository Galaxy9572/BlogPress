package com.blogpress.user.bean.dto;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色
 * @author JY
 */
@Data
public class UserRoleDTO implements Serializable {

    /** 主键 */
    private Long roleId;

    /** 用户ID */
    private Long userId;

    /** 用户角色ID */
    private String code;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除状态 */
    private Boolean isLogicDeleted;

    /** 版本号 */
    @Version
    private Long version;

}
