package com.blogpress.search.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支持的搜索类型枚举
 * @author JY
 */
public enum SearchTypeEnum {

    /** 全部类型 */
    ALL("all", null),

    /** 用户 */
    USER("user", "index_user"),

    /** 文章 */
    ARTICLE("article", "index_article");

    SearchTypeEnum(String code, String index) {
        this.code = code;
        this.index = index;
    }

    /** 搜索类型编码 */
    @Getter
    private final String code;

    /** 索引名称 */
    @Getter
    private final String index;

    public static SearchTypeEnum getByCode(String code) {
        return Arrays.stream(SearchTypeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

    public static List<String> listAllIndices() {
        return Arrays.stream(SearchTypeEnum.values()).filter(e -> e != ALL).map(SearchTypeEnum::getIndex).collect(Collectors.toList());
    }

}
