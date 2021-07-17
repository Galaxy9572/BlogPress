package com.blogpress.user.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 性别枚举
 * @author JY
 */
public enum GenderEnum {

    /** 男 */
    MALE("M", "男"),

    /** 女 */
    FEMALE("F", "女");

    @Getter
    private final String code;

    @Getter
    private final String desc;

    GenderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GenderEnum getByCode(String code){
        return Arrays.stream(GenderEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

}
