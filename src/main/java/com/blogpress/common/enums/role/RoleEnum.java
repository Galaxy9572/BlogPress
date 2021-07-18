package com.blogpress.common.enums.role;

import lombok.Getter;

import java.util.Arrays;

/**
 * 角色枚举
 * @author JY
 */
public enum RoleEnum {

    /** 用户角色 */
    USER("user", "用户"),

    /** 管理员角色 */
    ADMIN("admin","管理员");

    RoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Getter
    private final String code;

    @Getter
    private final String desc;

    public static RoleEnum getByCode(String code) {
        return Arrays.stream(RoleEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }



}
