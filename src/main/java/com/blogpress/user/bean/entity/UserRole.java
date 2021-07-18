package com.blogpress.user.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色
 * @author JY
 */
@Data
@TableName("user_role")
public class UserRole {

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private Long roleId;

    /** 用户ID */
    private Long userId;

    /** 角色编码 */
    private String code;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除状态 */
    @TableLogic
    private Boolean isLogicDeleted;

    /** 版本号 */
    @Version
    private Long version;

}
