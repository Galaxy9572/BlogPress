package com.blogpress.user.bean.dto;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体DTO类
 * @author JY
 */
@Data
public class UserDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    /** 主键 */
    private Long userId;

    /** 昵称 */
    private String nick;

    /** 密码 */
    private String password;

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
    private Boolean isLogicDeleted;

    /** 用户角色列表 */
    private List<UserRoleDTO> roles;

    /** 版本号 */
    @Version
    private Long version;

}
