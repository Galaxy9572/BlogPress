package com.blogpress.count.enums;

import java.util.Arrays;

/**
 * 内容类型枚举
 * @author JY
 */
public enum ContentTypeEnum {

    /** 文章 */
    ARTICLE("article", "文章"),

    /** 评论 */
    COMMENT("comment", "评论");

    ContentTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;

    private final String desc;

    public static ContentTypeEnum getByCode(String code) {
        return Arrays.stream(ContentTypeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
