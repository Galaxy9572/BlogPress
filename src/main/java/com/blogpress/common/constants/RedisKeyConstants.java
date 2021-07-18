package com.blogpress.common.constants;

import com.blogpress.count.enums.ContentTypeEnum;

import java.text.MessageFormat;

/**
 * Redis key常量
 * @author JY
 */
public class RedisKeyConstants {

    public static final String ARTICLE_DETAIL_KEY = "article_id_{0}";

    public static final String COUNT_DETAIL_KEY = "content_type_{0}_id_{1}";

    public static String articleKey(Long articleId){
        return MessageFormat.format(ARTICLE_DETAIL_KEY, articleId);
    }

    public static String countKey(ContentTypeEnum contentTypeEnum, Long contentId){
        return MessageFormat.format(COUNT_DETAIL_KEY, contentTypeEnum.getCode(), contentId);
    }

}
