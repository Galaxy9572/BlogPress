package com.blogpress.common.constants;

import java.text.MessageFormat;

/**
 * Redis key常量
 * @author JY
 */
public class RedisKeyConstants {

    public static final String ARTICLE_DETAIL_KEY = "article_id_{0}";

    public static String articleKey(Long articleId){
        return MessageFormat.format(ARTICLE_DETAIL_KEY, articleId);
    }

}
