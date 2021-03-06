package com.blogpress.user.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * @author JY
 */
@Data
@TableName("\"user\"")
public class User {

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /** 昵称 */
    private String nick;

    /** 密码 */
    private String passwordHash;

    /** 盐值 */
    private String salt;

    /** 性别 */
    private String gender;

    /** 生日 */
    private LocalDate birthday;

    /** 手机 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 国家 */
    private String country;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 街道 */
    private String street;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 用户状态 */
    private String userStatus;

    /** 逻辑删除状态 */
    @TableLogic
    private Boolean isLogicDeleted;

    /** 版本号 */
    @Version
    private Long version;

}
